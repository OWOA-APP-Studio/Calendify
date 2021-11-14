package com.owoa.calendify.schedule.read;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.owoa.calendify.R;
import com.owoa.calendify.category.read.CategoryReadPresenter;
import android.view.View;
import android.widget.ImageView;
import com.owoa.calendify.schedule.ScheduleCreateActivity;

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

        ImageView  scheduleCreateButton = (ImageView) findViewById(R.id.create_schedule_button);
        scheduleCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleReadActivity.this, ScheduleCreateActivity.class);
                startActivity(intent);
            }
        });

    }
}