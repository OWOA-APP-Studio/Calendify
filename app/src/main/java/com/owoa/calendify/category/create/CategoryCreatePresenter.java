package com.owoa.calendify.category.create;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.owoa.calendify.schedule.read.ScheduleReadActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.owoa.calendify.category.create.CategoryCreateData.REQUEST_CATEGORY_CREATE_URL;

public class CategoryCreatePresenter {
    Activity activity;

    ScheduleReadActivity scheduleReadActivity;

    public void setScheduleReadActivity(ScheduleReadActivity scheduleReadActivity) {
        this.scheduleReadActivity = scheduleReadActivity;
    }

    public CategoryCreatePresenter(Activity activity) {
        this.activity = activity;
    }


    public void createCategory(String uid, String name, String description, String color) {
        StringRequest createCategoryRequest = new StringRequest(Request.Method.POST, REQUEST_CATEGORY_CREATE_URL, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String result = jsonObject.getString("result");

                switch (result.charAt(0)) {
                    case 'S' :
                        Toast.makeText(activity, "카테고리를 생성했습니다.", Toast.LENGTH_SHORT).show();
                        activity.finish();
                        break;
                    case 'F' :
                    default:
                        Toast.makeText(activity, "서버측 오류로 생성되지 않았습니다.", Toast.LENGTH_SHORT).show();
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(activity.getApplicationContext(), "서버가 응답하지 않습니다." + error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", uid);
                params.put("name", name);
                params.put("description", description);
                params.put("color", color);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(createCategoryRequest);
    }
}
