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

import static com.owoa.calendify.friend.request.FriendRequestModel.REQUEST_FRIEND_REQUEST_LIST_URL;

public class FriendRequestPresenter {
    Activity activity;
    String uid;

    ListView listView;
    JSONArray friends;

    FriendRequestAdapter adapter;

    JSONObject jsonObject;

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public FriendRequestPresenter(Activity activity, String uid) {
        this.activity = activity;
        this.uid = uid;
    }

    public void loadRequestFriends() {
        StringRequest requestedFriend =  new StringRequest(Request.Method.POST, REQUEST_FRIEND_REQUEST_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("FRIEND-LOG",response);
                    Log.d("FRIEND-UID", uid);
                    jsonObject = new JSONObject(response);
                    friends = jsonObject.getJSONArray("친구 요청 목록");
                    adapter = new FriendRequestAdapter(activity, friends, uid);
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "오류"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("FRIEND-TAG", e.getMessage());
                    Log.d("FRIEND-UID", uid);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity.getApplicationContext(), "서버 통신 실패." + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("FRIEND-UID", uid);
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
        queue.add(requestedFriend);
    }

}
