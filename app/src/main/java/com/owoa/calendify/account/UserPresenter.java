package com.owoa.calendify.account;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class UserPresenter {
    private UserData userData;

    public UserPresenter(GoogleSignInAccount account) {
        userData = new UserData(account);
    }

    public UserPresenter(String id, String password) {
        userData = new UserData(id, password);
    }

    public UserData getInfoData() {
        return userData;
    }

    public boolean isValidInfo() {
        String idRegex = "^[a-zA-Z]{1}[a-zA-Z0-9]{4,20}$";
        String passwordRegex = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";

        return idRegex.matches(userData.getId()) || passwordRegex.matches(userData.getPassword());
    }

    public boolean isExistInfo() {
        return true;
    }
}
