package com.owoa.calendify.sign.up;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.owoa.calendify.R;

public class SignUpActivity extends AppCompatActivity {
    SignUpPresenter presenter = new SignUpPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        presenter.setActivity(this);
        EditText signUpId = (EditText) findViewById(R.id.sign_up_id);
        EditText signUpPassword = (EditText) findViewById(R.id.sign_up_password);
        EditText signUpEmail = (EditText) findViewById(R.id.sign_up_email);
        EditText signUpNickname = (EditText) findViewById(R.id.sign_up_nickname);

        Button sign_up_button = (Button) findViewById(R.id.sign_up_button);

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = signUpId.getText().toString();
                String password = signUpPassword.getText().toString();
                String email = signUpEmail.getText().toString();
                String nickname = signUpNickname.getText().toString();

                presenter.signUp(id, password, email, nickname);
            }
        });
    }
}