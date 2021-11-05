package com.owoa.calendify.loading;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.owoa.calendify.R;
import com.owoa.calendify.intro.IntroActivity;

public class LoadingActivity extends AppCompatActivity {
    // 3초 이후 자동 로그인 -> MainActivity 혹은 인트로 화면 이동
    private int loadingTime = 3000;
    LoadingPresenter loading = new LoadingPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadingActivity.this, IntroActivity.class);
                startActivity(intent);
            }
        }, loadingTime);

        loading.autoLogin();
    }
}