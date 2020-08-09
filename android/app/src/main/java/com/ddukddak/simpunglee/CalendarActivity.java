package com.ddukddak.simpunglee;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG_TEXT = "text";
    private static final String TAG_IMAGE = "image";

    ImageButton emojiSelection;
    Button save_button, edit_button;
    EditText diary_title, diary_content;
    TextView tv_today;
    int clickedYear = -999 , clickedMonth = -999, clickedDay = -999, clickedDayOfWeek = -999;

    List<Map<String, Object>> dialogItemList;

    int[] image = {R.drawable.ic_baseline_eco_24, R.drawable.ic_baseline_cloud_24, R.drawable.ic_baseline_brightness_2_24,
            R.drawable.ic_baseline_eco_24, R.drawable.ic_baseline_cloud_24, R.drawable.ic_baseline_brightness_2_24,
            R.drawable.ic_baseline_eco_24, R.drawable.ic_baseline_cloud_24, R.drawable.ic_baseline_brightness_2_24};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다이어리 저장
                saveDiary(clickedYear, clickedMonth, clickedDay);
            }
        });

        edit_button = findViewById(R.id.edit_button);
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

        diary_content = findViewById(R.id.diary_content);
        diary_title = findViewById(R.id.diary_title);

        emojiSelection = findViewById(R.id.emojiSelection);
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
            dialogItemList.add(itemMap);
        }

        Calendar cal = Calendar.getInstance();
        clickedYear = cal.get(Calendar.YEAR);
        clickedMonth= cal.get(Calendar.MONTH);
        clickedDay= cal.get(Calendar.DATE);

        DatePickerTimeline datePickerTimeline = findViewById(R.id.datePickerTimeline);

        tv_today = findViewById(R.id.tv_today);
        tv_today.setText("TODAY\n"+cal.get(Calendar.YEAR) + " "+changeMonth(cal.get(Calendar.MONTH)) + " "+cal.get(Calendar.DATE) +" "+ changeDayOfWeek(cal.get(Calendar.DAY_OF_WEEK)));

        // System.out.println("현재 시각은 " + clickedYear + "년도 " + clickedMonth + "월 " );
        datePickerTimeline.setInitialDate(clickedYear, clickedMonth, clickedDay-3);
        datePickerTimeline.setActiveDate(cal);

        // ActiveDate 다이어리 바로 가져오는 Task 여기에 작성하기

        // <3> 선택된 날짜, userid를 넘겨서 해당 날짜에 다이어리가 있는지 조회하기
        // SELECT로 조회해서 있으면 title, 무드, 내용을 가져와서
        // diary_title, diary_content, emojiSelection으로 보여주기
        // 없을 경우에는 없다는 메시지 반환

        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                // 다이어리 선택된 현재 날짜 저장
                clickedYear = year;
                clickedMonth = month;
                clickedDay = day;
                clickedDayOfWeek = dayOfWeek;

                // <3> 선택된 날짜, userid를 넘겨서 해당 날짜에 다이어리가 있는지 조회하기
                // SELECT로 조회해서 있으면 title, 무드, 내용을 가져와서
                // diary_title, diary_content, emojiSelection으로 보여주기
                // 없을 경우에는 없다는 메시지 반환

                // 1. 저장된 다이어리 내용이 없을 경우
                diary_title.setText("");
                diary_content.setText("");
                emojiSelection.setImageResource(R.drawable.ic_baseline_emoji_emotions_24);

                // 바로 일정 추가할 수 있게
                diary_title.setFocusable(true);
                diary_title.setFocusableInTouchMode(true);

                diary_content.setFocusableInTouchMode(true);
                diary_content.setFocusable(true);

                emojiSelection.setClickable(true);
                emojiSelection.setEnabled(true);

                // 2. 저장된 내용이 있을 경우
                // 수정하기 누르기 전에는 편집 불가능하게
                diary_title.setFocusable(false);
                diary_title.setClickable(false);

                diary_content.setClickable(false);
                diary_content.setFocusable(false);

                emojiSelection.setClickable(false);
                emojiSelection.setEnabled(false);

            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {
            }
        });

    }

    private void saveDiary(int year, int month, int day) {

        clickedMonth = clickedMonth +1; // 라이브러리가 1달 밀린 상태로 표기됨

        // <1, 2> 다이어리 내용 클릭된 날짜, userid, 제목, 내용, 무드 와 함께 서버로 넘기는 부분
        // 서버에서 처리할 것 -> 해당 날짜가 이미 있으면 UPDATE, 없어서 새로 등록하는 거면 INSERT

        // 입력된 내용이 모자랄 경우 예외처리
        if (diary_title.equals("")){
            Toast.makeText(CalendarActivity.this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
        }else if (diary_content.equals("")){
            Toast.makeText(CalendarActivity.this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
        }else if(diary_title.equals("") && diary_content.equals("")){
            Toast.makeText(CalendarActivity.this, "제목과 내용을 입력해주세요", Toast.LENGTH_SHORT).show();
        }

        System.out.println("현재 시각은 " + clickedYear + "년도 " + clickedMonth + "월 " + clickedDay + "일");

        // 저장 후 변경 불가능하게
        diary_title.setFocusable(false);
        diary_title.setClickable(false);

        diary_content.setClickable(false);
        diary_content.setFocusable(false);

        emojiSelection.setClickable(false);
        emojiSelection.setEnabled(false);
        return;
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
                new String[]{TAG_IMAGE},
                new int[]{R.id.alertDialogItemImageView});

        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                emojiSelection.setImageResource(image[position]);
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
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