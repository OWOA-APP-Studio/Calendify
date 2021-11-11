package com.owoa.calendify.sign.in;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.owoa.calendify.account.UserPresenter;
import com.owoa.calendify.schedule.read.ScheduleReadActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.owoa.calendify.sign.in.SignInData.REQUEST_SIGN_IN_URL;

public class SignInPresenter {
    private Activity activity;
    UserPresenter userPresenter;

    public SignInPresenter(Activity activity) {
        this.activity = activity;
    }

    public void signIn(String id, String password) {
        userPresenter = new UserPresenter(id, password);

        if(userPresenter.isValidSignInInfo()) {
            checkAccountInfo();
        } else {
            Toast.makeText(activity, "계정 정보가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkAccountInfo() {
        StringRequest signInRequest = new StringRequest(Request.Method.POST, REQUEST_SIGN_IN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")) {
                        Toast.makeText(activity, "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, ScheduleReadActivity.class);
                        intent.putExtra("uid", userPresenter.getInfoData().getId());
                        activity.startActivity(intent);
                        activity.finish();
                    }
                    else {
                        Toast.makeText(activity, "계정 정보를 다시 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "오류", Toast.LENGTH_SHORT).show();
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
                params.put("id", userPresenter.getInfoData().getId());
                params.put("password", userPresenter.getInfoData().getPassword());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(signInRequest);
    }
}
