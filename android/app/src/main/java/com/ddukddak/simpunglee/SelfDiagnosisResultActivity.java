package com.ddukddak.simpunglee;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.concurrent.ExecutionException;

public class SelfDiagnosisResultActivity extends AppCompatActivity {

    String url = "http://3.35.65.128:8080/simponglee/";

    private static final String TAG = "SelfDiagnosisResultActivity";
    TextView scoreTv, levelTv, commentTv;
    Button finishBtn, redoBtn;
    int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_diagnosis_result);

        scoreTv = (TextView)findViewById(R.id.depressionScoreTv);
        levelTv = (TextView)findViewById(R.id.depressionLevelTv);
        commentTv = (TextView)findViewById(R.id.depressionCheerText);
        finishBtn = (Button) findViewById(R.id.finishBtn);
        redoBtn = (Button) findViewById(R.id.redoBtn);

        userid = getIntent().getIntExtra("userid", 0);
        int categoryid = getIntent().getIntExtra("categoryid", 0);
        int rslt = getIntent().getIntExtra("selfDiagnosisScore", 0);
        String level = "";

        if(rslt >= 60) level = "심각";
        else if(rslt >= 30) level = "주의";
        else level = "관심";

        StringBuffer scoreSb = new StringBuffer();
        scoreSb.append("우울증 점수 : " + rslt);

        scoreTv.setText(scoreSb);
        levelTv.setText(level);

        if(level.equals("심각")) levelTv.setTextColor(Color.parseColor("#C00A32"));
        else if(level.equals("주의")) levelTv.setTextColor(Color.parseColor("#ffd633"));
        else levelTv.setTextColor(Color.parseColor("#009933"));

        commentTv.setText(getComment(categoryid, level));

        saveResult(userid, categoryid, rslt, level);

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        redoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in  = new Intent(getApplicationContext(), SelfDiagnosisCategoryActivity.class);
                in.putExtra("userid", userid);
                startActivity(in);
                finish();
            }
        });
    }

    private String getComment(int categoryid, String selfDiagnosisLevel) {
        ContentValues values = new ContentValues();

        values.put("categoryid", categoryid);
        values.put("selfDiagnosisLevel", selfDiagnosisLevel);

        NetworkTask getCommentTask = new NetworkTask(url + "selectComment", values);

        String receivedData;
        String returnData = "";

        try {
            receivedData = getCommentTask.execute().get();
            if (receivedData.equals("[]")){
                returnData = "";
            } else{
                returnData = parseJson(receivedData);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return returnData;
    }

    private String parseJson(String context){
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(context);
        JsonObject object = (JsonObject) jsonArray.get(0);

        String level = object.get("levelComment").getAsString();

        return level;
    }

    private void saveResult(int userid, int categoryid, int selfDiagnosisScore, String selfDiagnosisLevel) {
        ContentValues values = new ContentValues();

        Log.d(TAG, String.valueOf(userid));
        Log.d(TAG, String.valueOf(categoryid));
        Log.d(TAG, String.valueOf(selfDiagnosisScore));
        Log.d(TAG, String.valueOf(selfDiagnosisLevel));

        values.put("userid", userid);
        values.put("categoryid", categoryid);
        values.put("selfDiagnosisScore", selfDiagnosisScore);
        values.put("selfDiagnosisLevel", selfDiagnosisLevel);

        NetworkTask saveResultTask = new NetworkTask(url + "putResult", values);

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

