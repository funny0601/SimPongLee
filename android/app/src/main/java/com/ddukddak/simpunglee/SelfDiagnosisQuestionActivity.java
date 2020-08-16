package com.ddukddak.simpunglee;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SelfDiagnosisQuestionActivity extends AppCompatActivity {

    String url = "http://192.168.123.162:8080/";

    private static final String TAG = "SelfDiagnosisActivity";

    TextView questionTv;
    Button submitBtn, quitBtn;
    RadioGroup answerGrp;
    RadioButton score4, score3, score2, score1;

    List<String> questionList;

    public static int q = 0;
    public static int rslt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_diagnosis_question);

        questionList = new ArrayList<>();

        questionTv = findViewById(R.id.questionTv);

        submitBtn = findViewById(R.id.nextBtn);
        quitBtn = findViewById(R.id.quiBtn);

        answerGrp = findViewById(R.id.testAnswerGrp);
        score4 = findViewById(R.id.scoreRadio4);
        score3 = findViewById(R.id.scoreRadio3);
        score2 = findViewById(R.id.scoreRadio2);
        score1 = findViewById(R.id.scoreRadio1);

        questionList = getQuestion();

       questionTv.setText((q + 1) + ". " + questionList.get(q));

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answerGrp.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton uans = (RadioButton) findViewById(answerGrp.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();

                if(ansText.equals(score4.getText().toString())) rslt += 4;
                if(ansText.equals(score3.getText().toString())) rslt += 3;
                if(ansText.equals(score2.getText().toString())) rslt += 2;
                if(ansText.equals(score1.getText().toString())) rslt += 1;

                q++;

                if(q < questionList.size())
                {
                    questionTv.setText((q + 1) + ". " + questionList.get(q));
                }
                else
                {
                    Intent in = new Intent(getApplicationContext(), SelfDiagnosisResultActivity.class);
                    startActivity(in);
                }
                answerGrp.clearCheck();
            }
        });

        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), SelfDiagnosisResultActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<String> getQuestion() {
        ContentValues values = new ContentValues();

        values.put("categoryid", 1);

        NetworkTask getQuestionTask = new NetworkTask(url + "selectQuestion", values);

        String receivedData;
        List<String> returnData = new ArrayList<>();

        try {
            receivedData = getQuestionTask.execute().get();
            if (receivedData.equals("[]")){
                returnData = new ArrayList<>();
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

    private List<String> parseJson(String context){
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(context);

        List<String> stringList = new ArrayList<>();

        JsonObject q;
        for(int i = 0; i < jsonArray.size(); i++) {
            q = (JsonObject)jsonArray.get(i);
            stringList.add(q.get("question").getAsString());
        }

        return stringList;
    }
}


