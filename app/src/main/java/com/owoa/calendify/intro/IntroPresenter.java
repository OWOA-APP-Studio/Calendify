package com.owoa.calendify.intro;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.owoa.calendify.R;
import com.owoa.calendify.account.UserPresenter;
import com.owoa.calendify.sign.in.SignInActivity;
import com.owoa.calendify.sign.up.SignUpActivity;

import static android.content.ContentValues.TAG;

public class IntroPresenter {
    private static final int RC_SIGN_IN = 9001;
    IntroActivity activity;

    public void setActivity(IntroActivity activity) {
        this.activity = activity;
    }

    public void onClickSocialSignIn() {
        GoogleSignInClient mGoogleSignInClient;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(activity,gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void socialSignIn(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w(TAG, "handleSignInResult:failed code=" + e.getStatusCode());
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        UserPresenter userPresenter = new UserPresenter(account);
        Toast.makeText(activity, userPresenter.getInfoData().getName()+"님, 환영합니다." +
                "\nId:"+userPresenter.getInfoData().getId() +
                "\nEmail:"+userPresenter.getInfoData().getEmail(), Toast.LENGTH_SHORT).show();
    }

    public void onClickSignIn() {
        Intent signInIntent = new Intent(activity, SignInActivity.class);
        activity.startActivity(signInIntent);
    }

    public void onClickSignUp() {
        Intent signUpIntent = new Intent(activity, SignUpActivity.class);
        activity.startActivity(signUpIntent);
    }
}
