package com.owoa.calendify.account;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class UserPresenter {
    private UserData userData;
    String idRegex = "^[a-zA-Z]{1}[a-zA-Z0-9]{4,20}$";
    String passwordRegex = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$";
    String emailRegex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

    public UserPresenter(GoogleSignInAccount account) {
        userData = new UserData(account);
    }

    public UserPresenter(String id, String password) {
        userData = new UserData(id, password);
    }

    public UserPresenter(String id, String password, String email, String nickname) {
        userData = new UserData(id, password, email, nickname);
    }

    public UserData getInfoData() {
        return userData;
    }

    public boolean isValidSignInInfo() {
        return (userData.getId().matches(idRegex) && userData.getPassword().matches(passwordRegex));
    }

    public boolean isValidSignUpInfo() {
        return (userData.getId().matches(idRegex) && userData.getPassword().matches(passwordRegex) && userData.getEmail().matches(emailRegex));
    }

}
