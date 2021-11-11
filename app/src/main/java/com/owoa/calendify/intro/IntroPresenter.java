package com.owoa.calendify.intro;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.owoa.calendify.R;
import com.owoa.calendify.account.UserPresenter;
import com.owoa.calendify.schedule.read.ScheduleReadActivity;
import com.owoa.calendify.sign.in.SignInActivity;
import com.owoa.calendify.sign.up.SignUpActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.owoa.calendify.intro.IntroData.REQUEST_SOCIAL_SIGN_IN_URL;
import static com.owoa.calendify.intro.IntroData.REQUEST_SOCIAL_SIGN_UP_URL;

public class IntroPresenter {
    private static final int RC_SIGN_IN = 9001;
    Activity activity;
    UserPresenter userPresenter;

    public void setActivity(Activity activity) {
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
        userPresenter = new UserPresenter(account);
        checkAccountInfo(account.getId());
    }

    public void checkAccountInfo(String id) {
        StringRequest signInRequest = new StringRequest(Request.Method.POST, REQUEST_SOCIAL_SIGN_IN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")) {
                        Toast.makeText(activity, "자동 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, ScheduleReadActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                    else {
                        createSocialAccount();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity.getApplicationContext(), "서버 통신 실패." + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                return;
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(signInRequest);
    }

    public void createSocialAccount() {
        StringRequest signUpRequest = new StringRequest(Request.Method.POST, REQUEST_SOCIAL_SIGN_UP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")) {
                        Toast.makeText(activity, "계정을 생성했습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(activity, "계정 생성에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity.getApplicationContext(), "서버 통신 실패." + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", userPresenter.getInfoData().getId());
                params.put("email", userPresenter.getInfoData().getEmail());
                params.put("nickname", userPresenter.getInfoData().getNickname());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(signUpRequest);
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
