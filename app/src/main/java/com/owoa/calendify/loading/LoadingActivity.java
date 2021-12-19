package com.owoa.calendify.loading;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.owoa.calendify.R;

public class LoadingActivity extends AppCompatActivity {
    LoadingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        presenter = new LoadingPresenter(this);

        Handler handler = new Handler();
        // 3초 이후 자동 로그인 -> MainActivity 혹은 인트로 화면 이동
        int loadingTime = 3000;
        handler.postDelayed(() -> presenter.autoSignIn(), loadingTime);
    }
}