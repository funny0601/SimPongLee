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

import com.tyagiabhinav.dialogflowchatlibrary.Chatbot;
import com.tyagiabhinav.dialogflowchatlibrary.ChatbotActivity;
import com.tyagiabhinav.dialogflowchatlibrary.ChatbotSettings;
import com.tyagiabhinav.dialogflowchatlibrary.DialogflowCredentials;

import java.util.UUID;
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
                openChatbot(v); // 챗봇 액티비티로 넘어가기
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


    // 챗봇 뷰 새로 열기
    public void openChatbot(View view) {
        // provide your Dialogflow's Google Credential JSON saved under RAW folder in resources
        DialogflowCredentials.getInstance().setInputStream(getResources().openRawResource(R.raw.credential_file));


        ChatbotSettings.getInstance().setChatbot( new Chatbot.ChatbotBuilder()
                .setDoAutoWelcome(false) // True by Default, False if you do not want the Bot to greet the user Automatically. Dialogflow agent must have a welcome intent to handle this
                //.setChatBotAvatar(getDrawable(R.drawable.chatbot_icon))
                //.setChatUserAvatar(getDrawable(R.drawable.user_icon)) // 라이브러리 주인이 함수를 잘못 짜서 이거 해도 적용 안됨
                .setShowMic(false) // False by Default, True if you want to use Voice input from the user to chat
                .build());

        Intent intent = new Intent(HomeActivity.this, ChatbotActivity.class); // 라이브러리 코드 내 ChatbotActivity로 넘어감
        // ChatbotActivity는 코드 수정이 불가능해서 xml 변경도 색 변경이 할 수 있는 최대임!!
        Bundle bundle = new Bundle();

        // provide a UUID for your session with the Dialogflow agent
        bundle.putString(ChatbotActivity.SESSION_ID, UUID.randomUUID().toString());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}