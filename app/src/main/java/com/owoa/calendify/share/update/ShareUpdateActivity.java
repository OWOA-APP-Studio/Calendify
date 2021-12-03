package com.owoa.calendify.share.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.owoa.calendify.R;

public class ShareUpdateActivity extends AppCompatActivity {

    String uid;
    String tg_uid;

    String extra_req_uid;
    String extra_tg_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_update);

        Intent intent = getIntent();

        uid = intent.getStringExtra(getString(R.string.uid));

        extra_req_uid = intent.getStringExtra("req_uid");
        extra_tg_uid = intent.getStringExtra("tg_uid");

        setTargetUid();
    }

    private void setTargetUid() {
        if(uid.equals(extra_req_uid)) {
            tg_uid = extra_tg_uid;
        }
        else {
            tg_uid = extra_req_uid;
        }
    }
}