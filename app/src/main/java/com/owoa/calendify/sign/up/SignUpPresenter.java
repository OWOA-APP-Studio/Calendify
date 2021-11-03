package com.owoa.calendify.sign.up;

import android.widget.EditText;
import android.widget.Toast;

import com.owoa.calendify.R;
import com.owoa.calendify.account.UserPresenter;

public class SignUpPresenter {
    private SignUpActivity activity;
    UserPresenter userPresenter;

    public void setActivity(SignUpActivity activity) {
        this.activity = activity;
    }

    public void signUp(String id, String password, String email, String nickname) {
        userPresenter = new UserPresenter(id, password);

        if(userPresenter.isExistInfo()) {
            Toast.makeText(activity, userPresenter.getInfoData().getName()+"님, 환영합니다." +
                    "\nId:"+userPresenter.getInfoData().getId() +
                    "\nEmail:"+userPresenter.getInfoData().getEmail(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "가입 가능한 정보가 아닙니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
