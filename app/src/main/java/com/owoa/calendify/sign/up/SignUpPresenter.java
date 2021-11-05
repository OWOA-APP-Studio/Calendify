package com.owoa.calendify.sign.up;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.owoa.calendify.account.UserPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.owoa.calendify.sign.up.SignUpData.REQUEST_SIGN_UP_URL;

public class SignUpPresenter {
    private SignUpActivity activity;
    UserPresenter userPresenter;

    public void setActivity(SignUpActivity activity) {
        this.activity = activity;
    }

    public void signUp(String id, String password, String email, String nickname) {
        userPresenter = new UserPresenter(id, password, email, nickname);

        if(userPresenter.isValidSignUpInfo()) {
            createUserAccount();
        } else {
            Toast.makeText(activity, "가입 가능한 정보가 아닙니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void createUserAccount() {
        StringRequest signUpRequest = new StringRequest(Request.Method.POST, REQUEST_SIGN_UP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")) {
                        Toast.makeText(activity, "계정을 생성했습니다.", Toast.LENGTH_SHORT).show();
                        activity.finish();
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
                params.put("password", userPresenter.getInfoData().getPassword());
                params.put("email", userPresenter.getInfoData().getEmail());
                params.put("nickname", userPresenter.getInfoData().getNickname());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(signUpRequest);
    }
}
