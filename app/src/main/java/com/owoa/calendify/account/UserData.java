package com.owoa.calendify.account;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class UserData {
    private String email;
    private String id;
    private String nickname;
    private String password;

    public UserData(GoogleSignInAccount account) {
        this.email = account.getEmail();
        this.id = account.getId();
        this.nickname = account.getDisplayName();
    }

    public UserData(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public UserData(String id, String password, String email, String nickname) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
