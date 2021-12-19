package com.owoa.calendify.schedule.delete;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.owoa.calendify.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.owoa.calendify.schedule.delete.ScheduleDeleteData.REQUEST_SCHEDULE_DELETE_URL;

public class ScheduleDeletePresenter {
    Activity activity;
    String uid;
    String scheduleId;

    public ScheduleDeletePresenter(Activity activity, String uid, String scheduleId) {
        this.activity = activity;
        this.uid = uid;
        this.scheduleId = scheduleId;
    }

    public void deleteSchedule() {
        StringRequest scheduleDeleteRequest = new StringRequest(Request.Method.POST, REQUEST_SCHEDULE_DELETE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("success");
                    switch (result.charAt(0)) {
                        case 'S' :
                            Toast.makeText(activity, "일정을 생성했습니다.", Toast.LENGTH_SHORT).show();
                            break;
                        case 'F' :
                        default:
                            Toast.makeText(activity, "서버측 오류로 일정이 삭제되지 않았습니다.", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (Exception e) {
                    Log.d("SDD-ERR", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SDD-ERR", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(activity.getString(R.string.uid), uid);
                params.put("sch_id", scheduleId);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(scheduleDeleteRequest);
    }
}
