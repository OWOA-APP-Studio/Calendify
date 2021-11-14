package com.owoa.calendify.sign.in;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.owoa.calendify.R;


public class SignInActivity extends AppCompatActivity {
    SignInPresenter presenter;
    EditText signInID, signInPassword;
    Button signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        presenter = new SignInPresenter(this);

        signInID = findViewById(R.id.sign_up_id);
        signInPassword = findViewById(R.id.sign_up_password);
        signInButton = findViewById(R.id.sign_up_button);

        signInButton.setOnClickListener(v -> {
            String id = signInID.getText().toString();
            String password = signInPassword.getText().toString();

            presenter.signIn(id, password);
        });
    }
}