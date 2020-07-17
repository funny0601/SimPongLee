package com.ddukddak.simpunglee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    //private TextView tv_outPut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* xml 작업을 위한 주석 처리
        // 위젯에 대한 참조.
        tv_outPut = (TextView) findViewById(R.id.tv_outPut);

        // URL 설정.
        String url = "http://192.168.35.93:8080/"; // 본인 서버 주소 쓰삼!!

        // AsyncTask를 통해 HttpURLConnection 수행.
        NetworkTask networkTask = new NetworkTask(url, null);
        try {
            tv_outPut.setText(networkTask.execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        */

    }


}