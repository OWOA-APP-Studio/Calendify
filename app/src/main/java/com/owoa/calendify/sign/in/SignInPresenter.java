package com.owoa.calendify.sign.in;

import android.widget.Toast;

import com.owoa.calendify.account.UserPresenter;

public class SignInPresenter {
    private SignInActivity activity;
    UserPresenter userPresenter;

    public SignInPresenter(SignInActivity activity) {
        this.activity = activity;
    }

    public void signIn(String id, String password) {
        userPresenter = new UserPresenter(id, password);

        if(userPresenter.isValidInfo()) {
            updateUI();
        } else {
            Toast.makeText(activity, "계정 정보가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateUI() {
        Toast.makeText(activity, userPresenter.getInfoData().getName()+"님, 환영합니다." +
                "\nId:"+userPresenter.getInfoData().getId() +
                "\nEmail:"+userPresenter.getInfoData().getEmail(), Toast.LENGTH_SHORT).show();
    }
}
