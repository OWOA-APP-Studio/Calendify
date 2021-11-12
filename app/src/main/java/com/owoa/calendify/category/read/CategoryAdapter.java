package com.owoa.calendify.category.read;

import static com.owoa.calendify.category.read.CategoryData.REQUEST_READ_CATEGORY_URL;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CategoryAdapter extends RecyclerView.Adapter {
    private String[] categoryArray;
    private Activity activity;
    private String uid;

    public CategoryAdapter(String[] categoryArray, Activity activity, String uid) {
        this.categoryArray = categoryArray;
        this.activity = activity;
        this.uid = uid;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(new CategoryListItemView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CategoryListItemView)holder.itemView).displayItem(categoryArray[position], activity);
    }

    @Override
    public int getItemCount() {
        return categoryArray.length;
    }

    private class CategoryViewHolder extends RecyclerView.ViewHolder {
        public CategoryViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void requestUserCategory() {
        StringRequest categoryReadRequest = new StringRequest(Request.Method.POST, REQUEST_READ_CATEGORY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String categoryList = jsonObject.getString("category");

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
                params.put("id", uid);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(categoryReadRequest);
    }
}
