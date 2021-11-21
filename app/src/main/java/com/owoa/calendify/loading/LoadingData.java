package com.owoa.calendify.loading;

import android.app.Activity;
import android.content.SharedPreferences;

import com.owoa.calendify.R;

public class LoadingData {
    private final String id;
    private final String password;

    public LoadingData(LoadingActivity activity) {
        SharedPreferences autoSignInData = activity.getSharedPreferences("auto", Activity.MODE_PRIVATE);
        this.id = autoSignInData.getString(activity.getString(R.string.uid), null);
        this.password = autoSignInData.getString(activity.getString(R.string.password), null);
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
