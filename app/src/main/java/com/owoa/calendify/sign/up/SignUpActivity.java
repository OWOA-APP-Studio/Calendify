package com.owoa.calendify.sign.up;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.owoa.calendify.R;

public class SignUpActivity extends AppCompatActivity {
    SignUpPresenter presenter = new SignUpPresenter();
    EditText signUpId, signUpPassword, signUpEmail, signUpNickname;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        presenter.setActivity(this);

        signUpId = findViewById(R.id.sign_up_id);
        signUpPassword = findViewById(R.id.sign_up_password);
        signUpEmail = findViewById(R.id.sign_up_email);
        signUpNickname = findViewById(R.id.sign_up_nickname);

        signUpButton = findViewById(R.id.sign_up_button);

        signUpButton.setOnClickListener(v -> {
            String id = signUpId.getText().toString();
            String password = signUpPassword.getText().toString();
            String email = signUpEmail.getText().toString();
            String nickname = signUpNickname.getText().toString();

            presenter.signUp(id, password, email, nickname);
        });
    }
}