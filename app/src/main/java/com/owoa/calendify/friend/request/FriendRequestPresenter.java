package com.owoa.calendify.friend.request;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.owoa.calendify.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.owoa.calendify.friend.request.FriendRequestModel.REQUEST_FRIEND_READ_URL;

public class FriendRequestPresenter {
    Activity activity;
    FriendRequestAdapter adapter;

    JSONObject jsonObject;
    JSONArray friends;

    String uid;

    ListView friend_request_List;

    public void setFriendList(ListView friend_request_List) {
        this.friend_request_List = friend_request_List;
    }

    public FriendRequestPresenter(Activity activity) {
        this.activity = activity;
        this.uid = uid;
    }

    public void loadFriends() {
        StringRequest friendRequsetRequest = new StringRequest(Request.Method.POST, REQUEST_FRIEND_READ_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("FRIEND-LOG", response);
                    jsonObject = new JSONObject(response);
                    friends = jsonObject.getJSONArray("친구목록");
                    adapter = new FriendRequestAdapter(activity, friends);
                    friend_request_List.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "오류" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("FRIEND-TAG", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity.getApplicationContext(), "서버 통신 실패." + error.getMessage(), Toast.LENGTH_SHORT).show();
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
            queue.add(friendRequsetRequest);
    }
}

