package com.ddukddak.simpunglee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelfDiagnosisResultActivity extends AppCompatActivity {
    TextView scoreTv, levelTv;
    Button restartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_diagnosis_result);

        scoreTv = (TextView)findViewById(R.id.depressionScoreTv);
        levelTv = (TextView)findViewById(R.id.drpressionLevelTv);
        restartBtn = (Button) findViewById(R.id.restartBtn);

        StringBuffer scoreSb = new StringBuffer();
        scoreSb.append("우울증 점수 : " + SelfDiagnosisQuestionActivity.rslt + "\n");
        StringBuffer levelSb = new StringBuffer();
        levelSb.append("진단 레벨 : " + SelfDiagnosisQuestionActivity.rslt / 5 + "\n");
        scoreTv.setText(scoreSb);
        levelTv.setText(levelSb);

        SelfDiagnosisQuestionActivity.rslt = 0;
        SelfDiagnosisQuestionActivity.q = 0;

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //Intent in = new Intent(getApplicationContext(), SelfDiagnosisQuestionActivity.class);
                //startActivity(in);
            }
        });
    }

}

