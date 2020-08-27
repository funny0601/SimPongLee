package com.ddukddak.simpunglee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelfDiagnosisCategoryActivity extends AppCompatActivity {

    //String url = "http://192.168.56.1:8090/";
    GridView gridView;
    int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //userid = getIntent().getIntExtra("userid", 0);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_emoji, null);

        final GridView gridView = (GridView)view.findViewById(R.id.category_list);
    }
}
