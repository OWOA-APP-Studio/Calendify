package com.owoa.calendify.sign.up;

import android.content.Intent;
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
        StringRequest signUpRequest = new StringRequest(Request.Method.POST, REQUEST_SIGN_UP_URL, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");
                
                if(success.equals("1")) {
                    Toast.makeText(activity, "계정을 생성했습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, ScheduleReadActivity.class);
                    intent.putExtra(activity.getString(R.string.uid), userPresenter.getInfoData().getId());
                    activity.startActivity(intent);
                    activity.finish();
                }
                else {
                    Toast.makeText(activity, "계정 생성에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(activity.getApplicationContext(), "서버 통신 실패." + error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(activity.getString(R.string.uid), userPresenter.getInfoData().getId());
                params.put(activity.getString(R.string.password), userPresenter.getInfoData().getPassword());
                params.put(activity.getString(R.string.email), userPresenter.getInfoData().getEmail());
                params.put(activity.getString(R.string.nickname), userPresenter.getInfoData().getNickname());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(signUpRequest);
    }
}
