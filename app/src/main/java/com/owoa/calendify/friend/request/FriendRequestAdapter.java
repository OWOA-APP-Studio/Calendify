package com.owoa.calendify.friend.request;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.owoa.calendify.R;
import com.owoa.calendify.friend.FriendModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.owoa.calendify.friend.request.FriendRequestModel.REQUEST_FRIEND_REQUEST_LIST_URL;
import static com.owoa.calendify.friend.request.FriendRequestModel.REQUEST_FRIEND_REQUEST_UPDATE_URL;

public class FriendRequestAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<FriendModel> friendModels = new ArrayList<>();
    Activity activity;

    String uid;

    public FriendRequestAdapter(Activity activity, JSONArray friends,String uid) {
        this.uid = uid;
        this.activity = activity;
        this.layoutInflater = LayoutInflater.from(activity.getApplicationContext());

        for (int i = 0; i < friends.length(); i++) {
            try {
                friendModels.add(new FriendModel(friends.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public int getCount() {
        return friendModels.size();
    }

    @Override
    public Object getItem(int position) {
        return friendModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_request_friend, null);

        TextView id = view.findViewById(R.id.request_friend_id);
        TextView datetime = view.findViewById(R.id.request_friend_datetime);

        Button accept = view.findViewById(R.id.request_friend_accept_confirm);
        Button denied = view.findViewById(R.id.request_friend_denied_confirm);

        id.setText(friendModels.get(position).getRequestUid());
        datetime.setText(friendModels.get(position).getSelectDateTime());

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Todo: 친구 요청을 수락하면 친구 요청 리스트에 사라지도록 한다. slt_res = 1;
                denied.setVisibility(View.INVISIBLE);
                denied.setBackgroundTintList(view.getResources().getColorStateList(R.color.gray));
                String requestUid = friendModels.get(position).getRequestUid();
                String targetUid = friendModels.get(position).getTargetUid();

                updateFriendRequest(1, requestUid, targetUid);
            }
        });

        denied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Todo: 친구 요청을 거절하면 친구 요청 리스트에 사라지도록 한다.
                accept.setVisibility(View.INVISIBLE);
                denied.setBackgroundTintList(view.getResources().getColorStateList(R.color.gray));
                String requestUid = friendModels.get(position).getRequestUid();
                String targetUid = friendModels.get(position).getTargetUid();

                updateFriendRequest(-1, requestUid, targetUid);
            }
        });

        return view;
    }

    public void updateFriendRequest(int res, String requestUid, String targetUid ){
        StringRequest updateRequestFriend = new StringRequest(Request.Method.POST, REQUEST_FRIEND_REQUEST_UPDATE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("FRR-TAG", response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("FRR-ERR", error.getMessage());
                return;
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("slt_res", String.valueOf(res));
                params.put("tg_uid", targetUid);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(updateRequestFriend);
    }

}
