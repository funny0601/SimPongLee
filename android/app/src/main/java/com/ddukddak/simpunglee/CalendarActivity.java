package com.ddukddak.simpunglee;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CalendarActivity extends AppCompatActivity {

    String url = "http://192.168.123.162:8090/";

    private static final String TAG_TEXT = "text";
    private static final String TAG_IMAGE = "image";

    ImageButton emojiSelection, backButton, closeBtn;
    Button save_button, edit_button;
    EditText diary_title, diary_content;
    TextView tv_today;
    int clickedYear = -999 , clickedMonth = -999, clickedDay = -999, clickedDayOfWeek = -999;
    Intent BWFIntent; // 욕설 필터링
    List<Map<String, Object>> dialogItemList;

    int[] image = {R.drawable.happy, R.drawable.angry, R.drawable.sad,
            R.drawable.grumpy, R.drawable.sleepy, R.drawable.love,
            R.drawable.casual, R.drawable.dead, R.drawable.sick};

    String[] text = {"HAPPY", "ANGRY", "SAD",
            "GRUMPY", "SLEEPY", "LOVE",
            "CASUAL", "DEAD", "SICK"};

    int clickedImagePosition = 0;
    int userid = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // HomeActivity에서 userid를 받아와야 합니다아!

        // 뒤로가기 버튼
        backButton =(ImageButton)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 뒤로가기
            }
        });

        save_button = (Button)findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다이어리 저장
                saveDiary(clickedYear, clickedMonth+1, clickedDay);
            }
        });

        edit_button = (Button)findViewById(R.id.edit_button);
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 수정할 수 있게 editText 접근할 수 있게 하기
                diary_title.setFocusable(true);
                diary_title.setFocusableInTouchMode(true);

                diary_content.setFocusableInTouchMode(true);
                diary_content.setFocusable(true);

                emojiSelection.setClickable(true);
                emojiSelection.setEnabled(true);
            }
        });

        diary_content = (EditText)findViewById(R.id.diary_content);
        diary_title = (EditText)findViewById(R.id.diary_title);

        emojiSelection = (ImageButton)findViewById(R.id.emojiSelection);
        emojiSelection.setClickable(false);
        emojiSelection.setEnabled(false);
        emojiSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEmojiDialog();
            }
        });

        dialogItemList = new ArrayList<>();

        for(int i=0;i<image.length;i++)
        {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put(TAG_IMAGE, image[i]);
            itemMap.put(TAG_TEXT, text[i]);
            dialogItemList.add(itemMap);
        }

        Calendar cal = Calendar.getInstance();
        clickedYear = cal.get(Calendar.YEAR);
        clickedMonth= cal.get(Calendar.MONTH);
        clickedDay= cal.get(Calendar.DATE);

        DatePickerTimeline datePickerTimeline = findViewById(R.id.datePickerTimeline);

        tv_today = findViewById(R.id.tv_today);
        tv_today.setText("TODAY\n"+cal.get(Calendar.YEAR) + " "+changeMonth(cal.get(Calendar.MONTH)) + " "+cal.get(Calendar.DATE) +" "+ changeDayOfWeek(cal.get(Calendar.DAY_OF_WEEK)));


        datePickerTimeline.setInitialDate(clickedYear, clickedMonth, clickedDay-3);
        datePickerTimeline.setActiveDate(cal);

        // <3> 선택된 날짜, userid를 넘겨서 해당 날짜에 다이어리가 있는지 조회하기
        // SELECT로 조회해서 있으면 title, 무드, 내용을 가져와서
        // diary_title, diary_content, emojiSelection으로 보여주기

        // ActiveDate 다이어리 바로 가져오는 Task 여기에 작성하기
        // 서버 없을때는 아래 문장 주석처리하기
        setUpdateOption(getDiary(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE)));

        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {

                // 날짜를 새로 클릭하면 다이어리 emoji position도 기본으로 변경하기
                clickedImagePosition = 0;

                // 다이어리 선택된 현재 날짜 저장
                clickedYear = year;
                clickedMonth = month;
                clickedDay = day;
                clickedDayOfWeek = dayOfWeek;

                // <3> 선택된 날짜, userid를 넘겨서 해당 날짜에 다이어리가 있는지 조회하기
                // SELECT로 조회해서 있으면 title, 무드, 내용을 가져와서
                // diary_title, diary_content, emojiSelection으로 보여주기
                setUpdateOption(getDiary(clickedYear, clickedMonth+1, clickedDay));
            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {
            }
        });

    }
    
    private void setUpdateOption(boolean flag){
        if (flag){
            // 저장된 내용이 있을 경우
            // 수정하기 누르기 전에는 편집 불가능하게
            diary_title.setFocusable(false);
            diary_title.setClickable(false);

            diary_content.setClickable(false);
            diary_content.setFocusable(false);

            emojiSelection.setClickable(false);
            emojiSelection.setEnabled(false);
        }else{
            // 저장된 다이어리 내용이 없을 경우
            diary_title.setText("");
            diary_content.setText("");
            emojiSelection.setImageResource(R.drawable.happy);

            // 바로 일정 추가할 수 있게
            diary_title.setFocusable(true);
            diary_title.setFocusableInTouchMode(true);

            diary_content.setFocusableInTouchMode(true);
            diary_content.setFocusable(true);

            emojiSelection.setClickable(true);
            emojiSelection.setEnabled(true);
        }
    }
    private boolean getDiary(int year, int month, int day){

        boolean flag = false;
        String date = year + "" + month + "" + day;

        ContentValues values = new ContentValues();

        values.put("userid", userid);
        values.put("date", date);

        NetworkTask getDiaryTask = new NetworkTask(url+"selectDiary", values);

        try {
            String receivedText = getDiaryTask.execute().get();
            if (receivedText.equals("[]")){
                flag = false; // 등록된 다이어리 내용이 없으면
            } else{
                parseJson(receivedText);
                flag = true; // 등록된 다이어리 내용이 있으면 파싱해서 다이어리 보여주기
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return flag;
    }
    private void saveDiary(int year, int month, int day) {

        String date = year + "" + month + "" + day;
        // <1, 2> 다이어리 내용 클릭된 날짜, userid, 제목, 내용, 무드 와 함께 서버로 넘기는 부분
        // 서버에서 처리할 것 -> 해당 날짜가 이미 있으면 UPDATE, 없어서 새로 등록하는 거면 INSERT

        // 입력된 내용이 모자랄 경우 예외처리
        if (diary_title.equals("")){
            Toast.makeText(CalendarActivity.this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }else if (diary_content.equals("")){
            Toast.makeText(CalendarActivity.this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }else if(diary_title.equals("") && diary_content.equals("")){
            Toast.makeText(CalendarActivity.this, "제목과 내용을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        BWFIntent = new Intent(getApplicationContext(), BadwordFilter.class);
        BWFIntent.putExtra("TextToFilter", diary_content.getText().toString());
        System.out.println(diary_content.getText().toString());
        // Toast.makeText(getApplicationContext(), "되고잇냐?", Toast.LENGTH_LONG).show();
        startService(BWFIntent);

        // 저장버튼 눌렀으니까 수정 못함
        diary_title.setFocusable(false);
        diary_title.setClickable(false);

        diary_content.setClickable(false);
        diary_content.setFocusable(false);

        emojiSelection.setClickable(false);
        emojiSelection.setEnabled(false);

        ContentValues values = new ContentValues();

        values.put("userid", userid);
        values.put("title", String.valueOf(diary_title.getText()));
        values.put("body", String.valueOf(diary_content.getText()));
        values.put("mood", clickedImagePosition); //현재 클릭된 이모지로 저장하기
        values.put("date", date);

        NetworkTask saveDiaryTask = new NetworkTask(url+"putDiary", values);

        try {
            String response = saveDiaryTask.execute().get();
            Log.d("Server Result", response);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stopService(BWFIntent);
    }


    private void showEmojiDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CalendarActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_emoji, null);
        builder.setView(view);

        final GridView gridView = (GridView)view.findViewById(R.id.gridview_alterdialog_list);
        final AlertDialog dialog = builder.create();

        SimpleAdapter simpleAdapter = new SimpleAdapter(CalendarActivity.this, dialogItemList,
                R.layout.item_emoji,
                new String[]{TAG_IMAGE, TAG_TEXT},
                new int[]{R.id.alertDialogItemImageView, R.id.alertDialogEmojiName});

        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                emojiSelection.setImageResource(image[position]);
                // 클릭되면 새로 클릭된 이미지의 position으로 바뀜
                clickedImagePosition = position;
                dialog.dismiss();
            }
        });
// 2131296380
        closeBtn = (ImageButton) view.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이모티콘을 클릭하지 않았기 때문에 position 저장하지 않음
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void parseJson(String context){

        // 파싱해서 다이어리 화면에 보이게 하기
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(context);
        JsonObject object = (JsonObject) jsonArray.get(0);
        int mood = object.get("mood").getAsInt();
        String title = object.get("title").getAsString();
        String body = object.get("body").getAsString();

        diary_content.setText(body);
        diary_title.setText(title);
        emojiSelection.setImageResource(image[mood]);
    }

    private String changeDayOfWeek(int num) {
        String dayOfWeek = null;

        switch (num) {
            case 1:
                dayOfWeek = "SUN";
                break;
            case 2:
                dayOfWeek ="MON";
                break;
            case 3:
                dayOfWeek ="TUE";
                break;
            case 4:
                dayOfWeek ="WED";
                break;
            case 5:
                dayOfWeek = "THU";
                break;
            case 6:
                dayOfWeek ="FRI";
                break;
            case 7:
                dayOfWeek = "SAT";
                break;
        }
        return dayOfWeek;
    }

    private String changeMonth(int num) {
        String monthString = null;

        switch (num+1) {
            case 1:
                monthString = "JAN";
                break;
            case 2:
                monthString ="FEB";
                break;
            case 3:
                monthString ="MAR";
                break;
            case 4:
                monthString ="APR";
                break;
            case 5:
                monthString = "MAY";
                break;
            case 6:
                monthString ="JUN";
                break;
            case 7:
                monthString = "JUL";
                break;
            case 8:
                monthString ="AUG";
                break;
            case 9:
                monthString = "SEP";
                break;
            case 10:
                monthString ="OCT";
                break;
            case 11:
                monthString = "NOV";
                break;
            case 12:
                monthString = "DEC";
                break;
        }
        return monthString;
    }
}


