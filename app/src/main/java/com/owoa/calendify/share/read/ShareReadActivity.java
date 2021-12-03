package com.owoa.calendify.share.read;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.owoa.calendify.R;
import com.owoa.calendify.category.read.CategoryReadPresenter;
import com.owoa.calendify.friend.read.FriendReadActivity;
import com.owoa.calendify.schedule.create.ScheduleCreateActivity;
import com.owoa.calendify.schedule.read.ScheduleReadActivity;
import com.owoa.calendify.schedule.read.ScheduleReadPresenter;

import java.util.ArrayList;
import java.util.Arrays;

public class ShareReadActivity extends AppCompatActivity {
    String uid;
    Activity activity;
    String targetUid;

    CategoryReadPresenter categoryReadPresenter;
    String[] categories;
    ListView scheduleListView;

    public ShareReadActivity() {
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

        categoryReadPresenter = new CategoryReadPresenter(targetUid, activity);
        categoryReadPresenter.setRecyclerView(categoryView);
        categoryReadPresenter.loadUserCategory();
    }

    @NonNull
    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public void loadSchedule(int index) {
        ScheduleReadPresenter presenter = new ScheduleReadPresenter(activity, categories, index);
        presenter.setListView(findViewById(R.id.schedule_list));
        presenter.requestSchedules();
    }
}