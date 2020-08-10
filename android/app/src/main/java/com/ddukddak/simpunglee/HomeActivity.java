package com.ddukddak.simpunglee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class HomeActivity extends AppCompatActivity {
    //private TextView tv_outPut;

    LinearLayout diagnosisButton, chatbotButton, calendarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        diagnosisButton = (LinearLayout) findViewById(R.id.diagnosisButton);
        diagnosisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 진단테스트 액티비티로 넘어가기
            }
        });

        chatbotButton = (LinearLayout) findViewById(R.id.chatbotButton);
        chatbotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 챗봇 액티비티로 넘어가기
            }
        });

        calendarButton = (LinearLayout) findViewById(R.id.calendarButton);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });


    }
}