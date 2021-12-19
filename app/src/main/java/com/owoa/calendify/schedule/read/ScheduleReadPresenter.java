package com.owoa.calendify.schedule.read;

import android.app.Activity;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.owoa.calendify.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.owoa.calendify.schedule.read.ScheduleReadData.REQUEST_SCHEDULES_URL;

public class ScheduleReadPresenter {
    ScheduleReadAdapter adapter;
    Activity activity;
    String[] categories;
    JSONArray schedules;
    ListView listView;

    String uid;
    int category;

    public void setCategory(int category) {
        this.category = category;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ScheduleReadPresenter(Activity activity, String[] categories, int index) {
        this.activity = activity;
        this.categories = categories;
        this.category = index;
    }

    public void setListView(ListView listView) { this.listView = listView; }

    public void requestSchedules() {
        StringRequest scheduleRequest = new StringRequest(Request.Method.POST, REQUEST_SCHEDULES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("SCH-TAG", response);
                    JSONObject jsonObject = new JSONObject(response);
                    schedules = jsonObject.getJSONArray("일정");
                    adapter = new ScheduleReadAdapter(activity, schedules, categories);
                    adapter.setUid(uid);
                    listView.setAdapter(adapter);
                } catch (Exception e) {
                    Log.d("SCH-ERR", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SCH-ERR", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(activity.getString(R.string.uid), uid);
                params.put(activity.getString(R.string.category), categories[category]);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(scheduleRequest);
    }
}
