package com.owoa.calendify.schedule.read;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.owoa.calendify.R;
import com.owoa.calendify.category.read.CategoryReadPresenter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.owoa.calendify.schedule.ScheduleCreateActivity;
import com.owoa.calendify.schedule.ScheduleModel;

public class ScheduleReadActivity extends AppCompatActivity {
    ScheduleReadActivity activity;
    CategoryReadPresenter categoryReadPresenter;
    String[] categories;
    ListView scheduleListView;
    String uid;

    public String getUid() {
        return uid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_read);

        Intent intent = getIntent();
        uid = intent.getStringExtra(getString(R.string.uid));
        activity = this;

        scheduleListView = findViewById(R.id.schedule_list);

        RecyclerView categoryView = findViewById(R.id.category_list);
        categoryReadPresenter = new CategoryReadPresenter(uid, activity);
        categoryReadPresenter.setRecyclerView(categoryView);
        categoryReadPresenter.loadUserCategory();

        ImageView scheduleCreateButton = findViewById(R.id.create_schedule_button);
        scheduleCreateButton.setOnClickListener(view -> {
            Intent intent1 = new Intent(ScheduleReadActivity.this, ScheduleCreateActivity.class);
            startActivity(intent1);
        });
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public void loadSchedule(int index) {
        ScheduleReadPresenter presenter = new ScheduleReadPresenter(activity, categories, index);
        presenter.setListView(findViewById(R.id.schedule_list));
        presenter.requestSchedules();
    }
}