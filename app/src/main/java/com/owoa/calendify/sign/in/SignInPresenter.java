package com.owoa.calendify.sign.in;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.owoa.calendify.account.UserPresenter;

import java.util.HashMap;
import java.util.Map;

import static com.owoa.calendify.sign.in.SignInData.REQUEST_SIGN_IN_URL;

public class SignInPresenter {
    private SignInActivity activity;
    UserPresenter userPresenter;

    public SignInPresenter(SignInActivity activity) {
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
                Toast.makeText(activity.getApplicationContext(), "서버 통신 성공" + response.toString(), Toast.LENGTH_SHORT).show();
                Log.d("LOGIN-ERR", response);
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
