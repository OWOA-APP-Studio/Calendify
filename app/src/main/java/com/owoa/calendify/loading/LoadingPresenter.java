package com.owoa.calendify.loading;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.owoa.calendify.intro.IntroActivity;
import com.owoa.calendify.intro.IntroPresenter;
import com.owoa.calendify.sign.in.SignInPresenter;

public class LoadingPresenter {
    Activity activity;
    LoadingData data;
    SignInPresenter signInPresenter;
    IntroPresenter introPresenter = new IntroPresenter();

    public LoadingPresenter(LoadingActivity activity) {
        this.activity = activity;
        data = new LoadingData(activity);
    }

    public void autoSignIn() {
        Log.d("ASI-TAG", "자동 로그인 입니다." + data.getId());
        introPresenter.setActivity(activity);
        if(isExistData()) {
            signInPresenter = new SignInPresenter(activity);
            if(isSocialSignInData()) {
                Log.d("LP-TAG", "소셜 로그인 정보입니다.");
                introPresenter.setActivity(activity);
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
