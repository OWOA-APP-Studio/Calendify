package com.owoa.calendify.schedule.read;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.owoa.calendify.R;
import com.owoa.calendify.category.read.CategoryReadPresenter;

public class ScheduleReadActivity extends AppCompatActivity {
    ScheduleReadActivity activity;
    CategoryReadPresenter categoryReadPresenter;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_read);
        Intent intent = getIntent();
        uid = intent.getStringExtra(getString(R.string.uid));
        activity = this;

        RecyclerView categoryView = findViewById(R.id.category_list);
        categoryReadPresenter = new CategoryReadPresenter(uid, activity);
        categoryReadPresenter.setRecyclerView(categoryView);
        categoryReadPresenter.loadUserCategory();
    }
}