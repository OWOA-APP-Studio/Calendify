package com.owoa.calendify.account;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class UserData {
    private String email;
    private String id;
    private String name;
    private String password;

    public UserData(GoogleSignInAccount account) {
        this.email = account.getEmail();
        this.id = account.getId();
        this.name = account.getDisplayName();
    }

    public UserData(String id, String password) {
        this.id = id;
        this.password = password;
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

    public String getName() {
        return name;
    }

}
