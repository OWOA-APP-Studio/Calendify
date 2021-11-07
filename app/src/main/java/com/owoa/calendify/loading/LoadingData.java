package com.owoa.calendify.loading;

import android.app.Activity;
import android.content.SharedPreferences;

public class LoadingData {
    private String id;
    private String password;

    public LoadingData(LoadingActivity activity) {
        SharedPreferences autoSignInData = activity.getSharedPreferences("auto", Activity.MODE_PRIVATE);
        this.id = autoSignInData.getString("id", null);
        this.password = autoSignInData.getString("password", null);
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
