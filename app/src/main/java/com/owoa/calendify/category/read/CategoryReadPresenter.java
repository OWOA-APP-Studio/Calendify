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
import com.owoa.calendify.category.CategoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.owoa.calendify.category.read.CategoryReadData.REQUEST_READ_CATEGORY_URL;

public class CategoryReadPresenter {
    String categoryArray[];
    String uid;
    Activity activity;
    RecyclerView recyclerView;

    public CategoryReadPresenter(String uid, Activity activity) {
        this.uid = uid;
        this.activity = activity;
    }

    public void loadUserCategory() {
        StringRequest categoryReadRequest = new StringRequest(Request.Method.POST, REQUEST_READ_CATEGORY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("카테고리");

                    initializeUserCategory(jsonObject);

                    categoryArray = new String[jsonArray.length()];
                    for(int i = 0; i < jsonArray.length(); i++) {
                        categoryArray[i] = jsonArray.get(i).toString();
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
                Log.d("CATEGORY-TAG", error.getMessage());
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

    private void initializeUserCategory (JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("카테고리");
            categoryArray = new String[jsonArray.length()+1];
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject categoryData = jsonArray.getJSONObject(i);
                categoryArray[i] = categoryData.getString("ctg_nm");
            }
            categoryArray[jsonArray.length()] = "+";
        } catch (JSONException e) {

        } finally {
            recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(new CategoryAdapter(categoryArray, activity));
        }
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

}
