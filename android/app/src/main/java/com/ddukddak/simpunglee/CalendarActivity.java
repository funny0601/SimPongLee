package com.ddukddak.simpunglee;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

    int clickedYear, clickedMonth, clickedDay, clickedDayOfWeek;

    List<Map<String, Object>> dialogItemList;

    int[] image = {R.drawable.ic_baseline_eco_24, R.drawable.ic_baseline_cloud_24, R.drawable.ic_baseline_brightness_2_24};
    String[] text = {"포도", "체리", "수박"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다이어리 저장
                saveDiary(clickedYear, clickedMonth, clickedDay, clickedDayOfWeek);
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
            itemMap.put(TAG_TEXT, text[i]);

            dialogItemList.add(itemMap);
        }

        DatePickerTimeline datePickerTimeline = findViewById(R.id.datePickerTimeline);
// Set a Start date (Default, 1 Jan 1970)
        datePickerTimeline.setInitialDate(2019, 3, 21);
// Set a date Selected Listener
        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                // 다이어리 선택된 현재 날짜 저장
                clickedYear = year;
                clickedMonth = month;
                clickedDay = day;
                clickedDayOfWeek = dayOfWeek;

                // 다이어리 현재 날짜에 맞는 다이어리 저장된거 서버에서 가져와서
                // 다이어리에 보여주기
            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {
                // Do Something
            }
        });

// Disable date
        Date[] dates = {Calendar.getInstance().getTime()};
        datePickerTimeline.deactivateDates(dates);

    }

    private void saveDiary(int year, int month, int day, int dayOfWeek) {

        // 다이어리 내용 클릭된 날짜와 함께 서버로 넘기는 부분

        // 입력된 내용이 모자랄 경우 예외처리
        if (diary_title.equals("")){
            Toast.makeText(CalendarActivity.this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
        }else if (diary_content.equals("")){
            Toast.makeText(CalendarActivity.this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
        }else if(diary_title.equals("") && diary_content.equals("")){
            Toast.makeText(CalendarActivity.this, "제목과 내용을 입력해주세요", Toast.LENGTH_SHORT).show();
        }

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

        final ListView listview = (ListView)view.findViewById(R.id.listview_alterdialog_list);
        final AlertDialog dialog = builder.create();

        SimpleAdapter simpleAdapter = new SimpleAdapter(CalendarActivity.this, dialogItemList,
                R.layout.item_emoji,
                new String[]{TAG_IMAGE, TAG_TEXT},
                new int[]{R.id.alertDialogItemImageView, R.id.alertDialogItemTextView});

        listview.setAdapter(simpleAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CalendarActivity.this, text[position] + "를(을) 선택했습니다.", Toast.LENGTH_SHORT).show();
                emojiSelection.setImageResource(image[position]);
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}