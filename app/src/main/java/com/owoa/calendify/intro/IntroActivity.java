package com.owoa.calendify.intro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.owoa.calendify.R;

public class IntroActivity extends AppCompatActivity {
    IntroPresenter introPresenter = new IntroPresenter();
    Button socialLoginButton, signInButton, signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        introPresenter.setActivity(this);

        socialLoginButton = findViewById(R.id.social_login);
        signInButton = findViewById(R.id.sign_in);
        signUpButton = findViewById(R.id.sign_up);

        socialLoginButton.setOnClickListener(
                view -> introPresenter.onClickSocialSignIn()
        );

        signInButton.setOnClickListener(
                view -> introPresenter.onClickSignIn()
        );

        signUpButton.setOnClickListener(
                view -> introPresenter.onClickSignUp()
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        introPresenter.socialSignIn(requestCode, data);
    }
}