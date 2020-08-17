package com.ddukddak.simpunglee;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class SelfDiagnosisResultActivity extends AppCompatActivity {
    String url = "";

    private static final String TAG = "SelfDiagnosisResultActivity";
    TextView scoreTv, levelTv;
    Button restartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_diagnosis_result);

        scoreTv = (TextView)findViewById(R.id.depressionScoreTv);
        levelTv = (TextView)findViewById(R.id.drpressionLevelTv);
        restartBtn = (Button) findViewById(R.id.finishBtn);

        int userid = getIntent().getIntExtra("userid", 0);
        int categoryid = getIntent().getIntExtra("categoryid", 0);
        int rslt = getIntent().getIntExtra("selfDiagnosisScore", 0);
        int level = rslt / 5;

        StringBuffer scoreSb = new StringBuffer();
        scoreSb.append("우울증 점수 : " + rslt + "\n");
        StringBuffer levelSb = new StringBuffer();
        levelSb.append("진단 레벨 : " + level + "\n");
        scoreTv.setText(scoreSb);
        levelTv.setText(levelSb);

        saveResult(userid, categoryid, rslt, level);


        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void saveResult(int userid, int categoryid, int selfDiagnosisScore, int selfDiagnosisLevel) {
        ContentValues values = new ContentValues();

        Log.d(TAG, String.valueOf(userid));
        Log.d(TAG, String.valueOf(categoryid));
        Log.d(TAG, String.valueOf(selfDiagnosisScore));
        Log.d(TAG, String.valueOf(selfDiagnosisLevel));

        values.put("userid", userid);
        values.put("categoryid", categoryid);
        values.put("selfDiagnosisScore", selfDiagnosisScore);
        values.put("selfDiagnosisLevel", selfDiagnosisLevel);

        NetworkTask saveResultTask = new NetworkTask(url + "insertDiagnosisResult", values);

        try {
            String response = saveResultTask.execute().get();
            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

