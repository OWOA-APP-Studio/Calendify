package com.owoa.calendify.share.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import com.owoa.calendify.R;

import java.util.ArrayList;

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

        ArrayAdapter categoryAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, categories);
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
}