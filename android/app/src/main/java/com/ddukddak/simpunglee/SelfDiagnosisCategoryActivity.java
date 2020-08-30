package com.ddukddak.simpunglee;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelfDiagnosisCategoryActivity extends AppCompatActivity {

    String url = "http://3.35.65.128:8080/simpunglee/";
    final public static int REQUEST_CODE = 101;
    private int userid;
    List<SelfDiagnosisCategoryVO> categoryVOList;

    private Button btn_review;
    CategoryDialog categoryDialog;

    private Button selfDiagnosisDepressedBtn;
    private Button selfDiagnosisADHDBtn;
    private Button selfDiagnosisStressBtn;
    private Button selfDiagnosisPanicBtn;

    ImageButton quitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //userid = getIntent().getIntExtra("userid", 0);
        GridLayout gridLayout = (GridLayout)findViewById(R.id.category_list);

        userid = getIntent().getIntExtra("userid", 0);

        btn_review = (Button)findViewById(R.id.btn_review);

        final Intent intent = new Intent(SelfDiagnosisCategoryActivity.this, SelfDiagnosisQuestionActivity.class);
        intent.putExtra("userid", userid);

        selfDiagnosisDepressedBtn = (Button)findViewById(R.id.selfDiagnosisDepressedBtn);
        selfDiagnosisDepressedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("categoryid", 1);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        selfDiagnosisADHDBtn = (Button)findViewById(R.id.selfDiagnosisADHDBtn);
        selfDiagnosisADHDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("categoryid", 2);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        selfDiagnosisStressBtn = (Button)findViewById(R.id.selfDiagnosisStressBtn);
        selfDiagnosisStressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("categoryid", 3);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        selfDiagnosisPanicBtn = (Button)findViewById(R.id.selfDiagnosisPanicBtn);
        selfDiagnosisPanicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("categoryid", 4);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        quitBtn = findViewById(R.id.quiBtn);
        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void clickHere(View view) {
        categoryVOList = getCategory();

        CategoryRecyclerAdapter dataAdapter = new CategoryRecyclerAdapter(SelfDiagnosisCategoryActivity.this, categoryVOList);
        categoryDialog = new CategoryDialog(SelfDiagnosisCategoryActivity.this, dataAdapter);
        // 카테고리 화면 width, height 조정
        categoryDialog.show();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        Window window = categoryDialog.getWindow();

        int x = (int)(size.x*0.85f);
        int y= (int)(size.y*0.7f);

        window.setLayout(x,y);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE) {
            if(resultCode == -2) {
                finish();
            }
        }
    }

    private List<SelfDiagnosisCategoryVO> getCategory() {
        ContentValues values = new ContentValues();
        values.put("userid", userid);

        NetworkTask getQuestionTask = new NetworkTask(url + "selectLevel", values);

        String receivedData;
        List<SelfDiagnosisCategoryVO> returnData = new ArrayList<>();

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

    private List<SelfDiagnosisCategoryVO> parseJson(String context){

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(context);

        List<SelfDiagnosisCategoryVO> categoryVOList = new ArrayList<>();

        JsonObject q;
        SelfDiagnosisCategoryVO categoryVO;
        for(int i = 0; i < jsonArray.size(); i++) {
            q = (JsonObject)jsonArray.get(i);
            categoryVO = new SelfDiagnosisCategoryVO(
                    q.get("categoryName").getAsString(),
                    q.get("selfDiagnosisScore").getAsInt(),
                    q.get("selfDiagnosisLevel").getAsString());
            categoryVOList.add(categoryVO);
        }
        return categoryVOList;
    }
}
