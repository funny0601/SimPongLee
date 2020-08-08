package com.ddukddak.simpunglee;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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

    List<Map<String, Object>> dialogItemList;

    int[] image = {R.drawable.ic_baseline_eco_24, R.drawable.ic_baseline_cloud_24, R.drawable.ic_baseline_brightness_2_24};
    String[] text = {"포도", "체리", "수박"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        emojiSelection = findViewById(R.id.emojiSelection);
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
                // Do Something
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

    private void showEmojiDialog()
    {

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