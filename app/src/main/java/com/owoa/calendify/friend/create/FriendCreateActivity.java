package com.owoa.calendify.friend.create;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.owoa.calendify.R;
import com.owoa.calendify.friend.request.FriendRequestActivity;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.owoa.calendify.friend.create.FriendCreateData.REQUEST_ADD_FRIEND_URL;

//class Code {
//    public static int requestCode = 100;
//    public static int resultCode = 1;
//}
public class FriendCreateActivity extends Activity {
    FriendCreatePresenter presenter;
    FriendRequestActivity activity;
    Button confirmButton;
    EditText targetId;

    String uid;
    String targetUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FriendCreatePresenter(this);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_friend_add);
        confirmButton = findViewById(R.id.friend_add_confirm);

        Intent intent = getIntent();
        targetId = findViewById(R.id.friend_ID);
        uid = intent.getStringExtra(getString(R.string.uid));


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터 전달하기
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", targetId.getText().toString());
                targetUid = targetId.getText().toString();
                setResult(RESULT_OK, resultIntent);
                requestAddFriend();
                //액티비티(팝업) 닫기
                finish();
            }
        });

    }





    private void requestAddFriend() {
        StringRequest requestAddFriend = new StringRequest(Request.Method.POST, REQUEST_ADD_FRIEND_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("FRC", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("success");
                    switch (result.charAt(0)) {
                        case 'S':
                            Toast.makeText(getApplicationContext(), "친구 추가를 신청했습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case 'D':
                            Toast.makeText(getApplicationContext(), "이미 친구 신청을 보냈습니다.", Toast.LENGTH_SHORT).show();
                            break;
                        case 'F':
                        default:
                            Toast.makeText(getApplicationContext(), "상대방을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                            Log.d("FRC-ERR", response);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "서버 통신 실패." + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("FRC-ERR", error.getMessage());
                return;
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(getString(R.string.uid), uid);
                params.put("tg_uid", targetUid);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(requestAddFriend);
        //확인 버튼 클릭

    }

    @Override
    public boolean onTouchEvent (MotionEvent event){

        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed () {

        return;
    }
}
