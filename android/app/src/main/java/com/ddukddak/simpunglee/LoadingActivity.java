package com.ddukddak.simpunglee;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        int images[] ={R.drawable.happy, R.drawable.sad, R.drawable.angry,
                        R.drawable.casual, R.drawable.sleepy, R.drawable.dead,
                        R.drawable.grumpy, R.drawable.sick, R.drawable.love
        };

        viewFlipper = (ViewFlipper) findViewById(R.id.main_logo);

        for(int image: images){
            flipperImages(image);
        }
        startLoading();
    }

    public void flipperImages(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);      // 이미지 추가
        viewFlipper.setFlipInterval(300);       // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
        viewFlipper.setAutoStart(true);          // 자동 시작 유무 설정

        // animation
        //viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        //viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }
    private void startLoading(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2700);
    }
}
