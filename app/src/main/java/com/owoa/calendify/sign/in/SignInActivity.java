package com.owoa.calendify.sign.in;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.owoa.calendify.R;


public class SignInActivity extends AppCompatActivity {
    SignInPresenter presenter;
    EditText signInID, signInPassword;
    Button signInButton;
    Switch autoSignInSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signInID = (EditText) findViewById(R.id.sign_up_id);
        signInPassword = (EditText) findViewById(R.id.sign_up_password);
        signInButton = (Button) findViewById(R.id.sign_up_button);
        autoSignInSwitch = (Switch) findViewById(R.id.auto_sign_in_switch);

        presenter = new SignInPresenter(this);
        presenter.setAutoSwitch(autoSignInSwitch);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = signInID.getText().toString();
                String password = signInPassword.getText().toString();

                presenter.signIn(id, password);
            }
        });
    }
}