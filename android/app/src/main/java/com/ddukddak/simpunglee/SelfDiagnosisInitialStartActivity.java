package com.ddukddak.simpunglee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SelfDiagnosisInitialStartActivity extends AppCompatActivity {

    private int userid;
    private int categoryid;
    private String nickname;
    Button diagnosisStartBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_diagnosis_start);

        userid = getIntent().getIntExtra("userid", 0);
        categoryid = getIntent().getIntExtra("categoryid", 0);
        nickname = getIntent().getStringExtra("nickname");

        diagnosisStartBtn = (Button)findViewById(R.id.diagnosisStartBtn);
        diagnosisStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelfDiagnosisQuestionActivity.class);
                intent.putExtra("userid", userid);
                intent.putExtra("categoryid", 1);
                intent.putExtra("initial", 999);
                intent.putExtra("nickname", nickname); // 처음 가입하면 사용자 닉네임 계속 가져가야함
                startActivity(intent);
                finish();
            }
        });
    }
}
