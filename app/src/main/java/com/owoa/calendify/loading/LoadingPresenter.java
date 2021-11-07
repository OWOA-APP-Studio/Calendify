package com.owoa.calendify.loading;

import android.content.Intent;

import com.owoa.calendify.intro.IntroActivity;
import com.owoa.calendify.intro.IntroPresenter;
import com.owoa.calendify.sign.in.SignInPresenter;

public class LoadingPresenter {
    LoadingActivity activity;
    LoadingData data;
    SignInPresenter signInPresenter;
    IntroPresenter introPresenter = new IntroPresenter();

    public LoadingPresenter(LoadingActivity activity) {
        this.activity = activity;
        data = new LoadingData(activity);
    }

    public void autoSignIn() {
        if(isExistData()) {
            signInPresenter = new SignInPresenter(activity);
            if(isSocialSignInData()) {
                introPresenter.checkAccountInfo(data.getId());
            }
            else {
                signInPresenter.signIn(data.getId(), data.getPassword());
            }
        }
        else {
            Intent intent = new Intent(activity, IntroActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
    }

    public boolean isSocialSignInData() {
        return data.getId().length() == 21;
    }

    public boolean isExistData() {
        return (data.getId() != null || data.getPassword() != null);
    }
}
