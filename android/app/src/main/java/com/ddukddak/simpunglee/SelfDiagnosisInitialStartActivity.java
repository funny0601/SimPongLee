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
    Button diagnosisStartBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_diagnosis_start);

        userid = getIntent().getIntExtra("userid", 0);
        categoryid = getIntent().getIntExtra("categoryid", 0);

        diagnosisStartBtn = (Button)findViewById(R.id.diagnosisStartBtn);
        diagnosisStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelfDiagnosisQuestionActivity.class);
                intent.putExtra("userid", userid);
                intent.putExtra("categoryid", 1);
                startActivity(intent);
                finish();
            }
        });
    }
}
