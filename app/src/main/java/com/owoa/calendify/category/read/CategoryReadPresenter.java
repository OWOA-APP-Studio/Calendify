package com.owoa.calendify.category.read;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.owoa.calendify.R;
import com.owoa.calendify.schedule.read.ScheduleReadActivity;
import com.owoa.calendify.share.read.ShareReadActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.owoa.calendify.category.read.CategoryReadData.REQUEST_READ_CATEGORY_URL;
import static com.owoa.calendify.category.read.CategoryReadData.REQUEST_READ_FRIEND_CATEGORY_URL;

public class CategoryReadPresenter {
    String categories[];
    String uid;
    Activity activity;
    RecyclerView recyclerView;
    CategoryReadAdapter adapter;
    JSONObject jsonObject;
    ShareReadActivity shareReadActivity;

    ScheduleReadActivity scheduleReadActivity;

    public CategoryReadAdapter getAdapter() {
        return adapter;
    }

    public String[] getCategories() {
        return categories;
    }

    public CategoryReadPresenter(String uid, Activity activity) {
        this.uid = uid;
        this.activity = activity;
    }

    public void setShareReadActivity(ShareReadActivity shareReadActivity) {
        this.shareReadActivity = shareReadActivity;
    }

    public void loadUserCategory() {
        StringRequest categoryReadRequest = new StringRequest(Request.Method.POST, REQUEST_READ_CATEGORY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("CATEGORY-CRP48",response);
                    jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("카테고리");

                    initializeUserCategory(0);

                    categories = new String[jsonArray.length()];
                    for(int i = 0; i < jsonArray.length(); i++) {
                        categories[i] = jsonArray.get(i).toString();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "오류"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("CATEGORY-TAG", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity.getApplicationContext(), "서버 통신 실패." + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("CATEGORY-CRP68", error.getMessage());
                return;
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(activity.getString(R.string.uid), uid);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(categoryReadRequest);
    }

    public void loadFriendCategory(String targetUid) {
        StringRequest friendCategoryReadRequest = new StringRequest(Request.Method.POST, REQUEST_READ_FRIEND_CATEGORY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("CATEGORY-CRP89",response);
                    jsonObject = new JSONObject(response);

                    initializeFriendCategory(targetUid, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "오류"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("CATEGORY-CRP98", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity.getApplicationContext(), "서버 통신 실패." + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("CATEGORY-CRP105", error.getMessage());
                return;
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(activity.getString(R.string.uid), uid);
                params.put("target_uid", targetUid);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(friendCategoryReadRequest);
    }

    public void initializeFriendCategory(String targetUid, int index) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("친구 카테고리");
            categories = new String[jsonArray.length()];
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject categoryData = jsonArray.getJSONObject(i);
                categories[i] = categoryData.getString("ctg_nm");
            };
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(shareReadActivity != null) {
                adapter = new CategoryReadAdapter(categories, activity, targetUid, true);
                adapter.setPresenter(this);
                ShareReadActivity shareReadActivity = (ShareReadActivity) activity;
                shareReadActivity.setCategories(categories);
                shareReadActivity.loadSchedule(index);
            }
            else {
                adapter = new CategoryReadAdapter(categories, activity, targetUid, false);
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(adapter);
        }
    }

    public void initializeUserCategory (int index) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("카테고리");
            categories = new String[jsonArray.length()+1];
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject categoryData = jsonArray.getJSONObject(i);
                categories[i] = categoryData.getString("ctg_nm");
            }
            categories[jsonArray.length()] = "+";

            ScheduleReadActivity scheduleReadActivity = (ScheduleReadActivity) activity;
            scheduleReadActivity.setCategories(categories);
            scheduleReadActivity.loadSchedule(index);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            adapter = new CategoryReadAdapter(categories, activity, this);
            adapter.setScheduleReadActivity(scheduleReadActivity);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(adapter);
        }
    }



    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }


    public void setMainActivity(ScheduleReadActivity scheduleReadActivity) {
        this.scheduleReadActivity = scheduleReadActivity;
    }
}
