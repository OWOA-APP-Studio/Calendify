package com.owoa.calendify.sign.in;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.owoa.calendify.R;
import com.owoa.calendify.account.UserPresenter;
import com.owoa.calendify.schedule.read.ScheduleReadActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.owoa.calendify.sign.in.SignInData.REQUEST_SIGN_IN_URL;

public class SignInPresenter {
    private final Activity activity;
    UserPresenter userPresenter;
    Switch autoSignInSwitch;

    public SignInPresenter(Activity activity) {
        this.activity = activity;
    }

    public void setAutoSwitch(Switch autoSignInSwitch) {
        this.autoSignInSwitch = autoSignInSwitch;
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
        StringRequest signInRequest = new StringRequest(Request.Method.POST, REQUEST_SIGN_IN_URL, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");

                if(success.equals("1")) {
                    Toast.makeText(activity, "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, ScheduleReadActivity.class);
                    intent.putExtra(activity.getString(R.string.uid), userPresenter.getInfoData().getId());
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
        }, error -> Toast.makeText(activity.getApplicationContext(), "서버 통신 실패." + error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", userPresenter.getInfoData().getId());
                params.put("password", userPresenter.getInfoData().getPassword());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(signInRequest);
    }


}
