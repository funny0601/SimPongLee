package com.ddukddak.simpunglee;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.omidh.liquidradiobutton.LiquidRadioButton;

public class SelfDiagnosisQuestionActivity extends AppCompatActivity implements QuestionRecyclerAdapter.ListItemClickListener {

    String url = "";

    private static final String TAG = "SelfDiagnosisActivity";

    private int userid;
    int problemCount = 0;

    private Button submitBtn;
    private ImageButton quitBtn;

    private com.daimajia.numberprogressbar.NumberProgressBar progress_bar;
    List<selfDiagnosisQuestionVO> questionList;

    int [] questionProblemSolved;
    private int rslt = -999;
    private RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    private QuestionRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_diagnosis_question);

        userid = getIntent().getIntExtra("userid", 0);

        submitBtn = findViewById(R.id.submitBtn);
        quitBtn = findViewById(R.id.quiBtn);
        progress_bar = findViewById(R.id.progress_bar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        QuestionRecyclerAdapter.ListItemClickListener myListener = new QuestionRecyclerAdapter.ListItemClickListener() {
            @Override
            public void onListItemClick(int clickItemIndex, int pointSelected) {
                questionList.get(clickItemIndex).setSelectPoint(pointSelected);
                System.out.println(questionList.get(clickItemIndex).getQuestion());
                System.out.println(questionList.get(clickItemIndex).getSelectPoint());
                // 문제풀면 문제 푼 수 증가시키기
                if (questionProblemSolved[clickItemIndex]==0){
                    questionProblemSolved[clickItemIndex]=1;
                } // 문제 처음푸는 거면 1로 숫자 올림

                progress_bar.setProgress(sumList(questionProblemSolved)*5);
                mAdapter.notifyDataSetChanged();
            }
        };

        // 서버에서 질문 목록 받아서 저장해서 어댑터로 넘기기
        questionList = getQuestion();
        questionProblemSolved = new int[questionList.size()];
        Arrays.fill(questionProblemSolved,0);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(this,
                        new LinearLayoutManager(this).getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mAdapter = new QuestionRecyclerAdapter(this, myListener, questionList);
        mRecyclerView.setAdapter(mAdapter);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rslt = mAdapter.getPointSum();
                if(rslt==-1){
                    Toast.makeText(getApplicationContext(), "모든 문제를 풀어주세요.", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    Intent in = new Intent(getApplicationContext(), SelfDiagnosisResultActivity.class);
                    System.out.println("최종점수"+rslt);
                    in.putExtra("userid", userid);
                    in.putExtra("categoryid", 1);
                    in.putExtra("selfDiagnosisScore", rslt);
                    startActivity(in);
                    finish();
                }
            }
        });


        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private List<selfDiagnosisQuestionVO> getQuestion() {
        ContentValues values = new ContentValues();

        values.put("categoryid", 1);

        NetworkTask getQuestionTask = new NetworkTask(url + "selectQuestion", values);

        String receivedData;
        List<selfDiagnosisQuestionVO> returnData = new ArrayList<>();

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

    private List<selfDiagnosisQuestionVO> parseJson(String context){

        // String ArrayList에서 selfDiagnosisQuestionVO 타입의 List로 바꿨어요
        // RecyclerView에서 보여주기에는 이게 쬐에끔더 편한거 같더라구여..!?

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(context);

        List<selfDiagnosisQuestionVO> questionVOList = new ArrayList<>();

        JsonObject q;
        for(int i = 0; i < jsonArray.size(); i++) {
            q = (JsonObject)jsonArray.get(i);
            questionVOList.add(new selfDiagnosisQuestionVO(q.get("question").getAsString()));
        }
        return questionVOList;
    }

    public int sumList(int[] array){
        int sum = 0;
        for(int i:array){
            sum = sum + i;
        }
        return sum;
    }

    @Override
    public void onListItemClick(int clickItemIndex, int pointSelected) {
        questionList.get(clickItemIndex).setSelectPoint(pointSelected);
        System.out.println(questionList.get(clickItemIndex).getQuestion());
        System.out.println(questionList.get(clickItemIndex).getSelectPoint());
        mAdapter.notifyDataSetChanged();
    }
}
