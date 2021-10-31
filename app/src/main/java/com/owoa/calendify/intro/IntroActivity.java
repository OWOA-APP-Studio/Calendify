package com.owoa.calendify.intro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.owoa.calendify.R;

public class IntroActivity extends AppCompatActivity {
    IntroPresenter introPresenter = new IntroPresenter();
    IntroActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        activity = this;

        Button socialLoginButton = (Button) findViewById(R.id.social_login);
        Button signInButton = (Button) findViewById(R.id.sign_in);
        Button signUpButton = (Button) findViewById(R.id.sign_up);

        socialLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                introPresenter.socialLogin();
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                introPresenter.signIn();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                introPresenter.signUp();
            }
        });
    }
}