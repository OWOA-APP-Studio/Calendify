package com.owoa.calendify.share.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.owoa.calendify.R;
import com.owoa.calendify.schedule.read.ScheduleReadData;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.owoa.calendify.share.update.ShareUpdateData.REQUEST_SHARE_CATEGORY_CREATE_URL;
import static com.owoa.calendify.share.update.ShareUpdateData.REQUEST_SHARE_CATEGORY_DELETE_URL;
import static com.owoa.calendify.share.update.ShareUpdateData.REQUEST_SHARE_CATEGORY_READ_URL;

public class ShareUpdateActivity extends AppCompatActivity {
    String uid;
    String tg_uid;

    String extra_req_uid;
    String extra_tg_uid;

    Switch categorySwitch;
    private Spinner category_spinner;
    private ArrayList categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_update);

        Intent intent = getIntent();

        categorySwitch = findViewById(R.id.share_switch);
        uid = getIntent().getStringExtra(getString(R.string.uid));
        categories = (ArrayList) intent.getSerializableExtra("categories");
        category_spinner = findViewById(R.id.share_update_category_spinner);

        extra_req_uid = intent.getStringExtra("req_uid");
        extra_tg_uid = intent.getStringExtra("tg_uid");

        setTargetUid();

        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringRequest request = new StringRequest(Request.Method.POST, REQUEST_SHARE_CATEGORY_READ_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SUA-RES", response);
                        try{
                            categorySwitch.setOnCheckedChangeListener(null);
                            categorySwitch.setChecked(false);

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("공유 카테고리 목록");
                            ShareUpdateData data;
                            if(jsonArray.length() != 0) {
                                data = new ShareUpdateData(jsonArray.getJSONObject(0));
                            }
                            else {
                                data = new ShareUpdateData(tg_uid);
                            }


                            if(data.getFriend_category_uid() != null) {
                                categorySwitch.setChecked(true);
                            }

                            categorySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if(isChecked) {
                                        StringRequest createShareCategoryRequest = new StringRequest(Request.Method.POST, REQUEST_SHARE_CATEGORY_CREATE_URL, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    Log.d("SUA-RES", response);
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    String result = jsonObject.getString("success");
                                                    switch (result.charAt(0)) {
                                                        case 'S' :
                                                            Toast.makeText(getApplicationContext(), "카테고리를 공유했습니다.", Toast.LENGTH_SHORT).show();
                                                            break;
                                                        case 'F' :
                                                        default:
                                                            Toast.makeText(getApplicationContext(), "서버측 오류로 변경되지 않았습니다.", Toast.LENGTH_SHORT).show();
                                                            break;
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.d("SUA-ERR", error.getMessage());
                                            }
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> params = new HashMap<>();
                                                params.put(getString(R.string.uid), uid);
                                                params.put("target_uid", tg_uid);
                                                params.put("category", category_spinner.getSelectedItem().toString());
                                                return params;
                                            }
                                        };
                                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                        queue.add(createShareCategoryRequest);
                                    }
                                    else {
                                        StringRequest deleteShareCategoryRequest = new StringRequest(Request.Method.POST, REQUEST_SHARE_CATEGORY_DELETE_URL, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    Log.d("SUA-RES", response);
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    String result = jsonObject.getString("success");
                                                    switch (result.charAt(0)) {
                                                        case 'S' :
                                                            Toast.makeText(getApplicationContext(), "공유를 중단했습니다.", Toast.LENGTH_SHORT).show();
                                                            break;
                                                        case 'F' :
                                                        default:
                                                            Toast.makeText(getApplicationContext(), "서버측 오류로 변경되지 않았습니다.", Toast.LENGTH_SHORT).show();
                                                            break;
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.d("SUA-ERR", error.getMessage());
                                            }
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> params = new HashMap<>();
                                                params.put(getString(R.string.uid), uid);
                                                params.put("target_uid", tg_uid);
                                                params.put("category", category_spinner.getSelectedItem().toString());
                                                return params;
                                            }
                                        };

                                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                        queue.add(deleteShareCategoryRequest);
                                    }
                                }
                            });

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("SUA-ERR", error.getMessage());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put(getString(R.string.uid), uid);
                        params.put("target_uid", tg_uid);
                        params.put("category", category_spinner.getSelectedItem().toString());

                        return params;
                    }
                };
                category_spinner.getSelectedItem().toString();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(request);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        requestCategoryList();

        ArrayAdapter categoryAdapter = new ArrayAdapter<> (getApplication(), android.R.layout.simple_spinner_dropdown_item, categories);
        category_spinner.setAdapter(categoryAdapter);
    }

    private void setTargetUid() {
        if(uid.equals(extra_req_uid)) {
            tg_uid = extra_tg_uid;
        }
        else {
            tg_uid = extra_req_uid;
        }
    }


    public void requestCategoryList() {
    }
}