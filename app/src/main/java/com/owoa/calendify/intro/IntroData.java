package com.owoa.calendify.intro;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class IntroData {
    private String email;
    private String id;
    private String name;

    public IntroData(GoogleSignInAccount account) {
        this.email = account.getEmail();
        this.id = account.getId();
        this.name = account.getDisplayName();
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
