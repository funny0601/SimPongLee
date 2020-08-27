package com.ddukddak.simpunglee;

import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

    String url = "http://3.35.65.128:8080/simponglee/";
    GridLayout gridLayout;
    int userid;
    List<SelfDiagnosisCategoryVO> categoryVOList;

    private RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    private CategoryRecyclerAdapter mAdapter;

    private Button btn_review;
    private ImageButton closeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //userid = getIntent().getIntExtra("userid", 0);
        GridLayout gridLayout = (GridLayout)findViewById(R.id.category_list);

        btn_review = (Button)findViewById(R.id.btn_review);
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryDialogbox();
            }
        });
    }

    private void showCategoryDialogbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SelfDiagnosisCategoryActivity.this);
        LayoutInflater inflater = LayoutInflater.from(SelfDiagnosisCategoryActivity.this);
        View view = inflater.inflate(R.layout.dialog_category, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCategory);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getApplicationContext(),
                        new LinearLayoutManager(getApplicationContext()).getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        // 서버에서 카테고리 점수, 등급, 이름 목록 받아서 저장해서 어댑터로 넘기기
        //categoryList = getCategory();

        categoryVOList = new ArrayList<>();
        categoryVOList.add(new SelfDiagnosisCategoryVO("우울증", 100, "심각"));
        categoryVOList.add(new SelfDiagnosisCategoryVO("우울증", 100, "심각"));
        categoryVOList.add(new SelfDiagnosisCategoryVO("우울증", 100, "심각"));
        categoryVOList.add(new SelfDiagnosisCategoryVO("우울증", 100, "심각"));


        mAdapter = new CategoryRecyclerAdapter(SelfDiagnosisCategoryActivity.this, categoryVOList);
        mRecyclerView.setAdapter(mAdapter);

        closeBtn = (ImageButton) view.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private List<SelfDiagnosisCategoryVO> getCategory() {
        ContentValues values = new ContentValues();
        // 여기 카테고리 이름, 점수 등급 받아오는 코드 수정해서 작성해주세요
        // 기존 코드 그대로 갖고온거에요!!
        values.put("categoryid", 1);

        NetworkTask getQuestionTask = new NetworkTask(url + "selectQuestion", values);

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

        // String ArrayList에서 selfDiagnosisQuestionVO 타입의 List로 바꿨어요
        // RecyclerView에서 보여주기에는 이게 쬐에끔더 편한거 같더라구여..!?

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(context);

        List<SelfDiagnosisCategoryVO> categoryVOList = new ArrayList<>();

        JsonObject q;
        for(int i = 0; i < jsonArray.size(); i++) {
            q = (JsonObject)jsonArray.get(i);
            // SelfDiagnosisQuestionVO 보시고 getCategoryName, getGrade웅앵 함수들 보시고
            // 필요한거에 맞춰서 넣으시면 돼요!!
            // 모르면 자유이용권을 써주세여~!

            //categoryVOList.add(new SelfDiagnosisCategoryVO(q.get("question").getAsString()));
        }
        return categoryVOList;
    }
}
