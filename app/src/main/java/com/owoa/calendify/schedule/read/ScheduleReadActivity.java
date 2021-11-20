package com.owoa.calendify.schedule.read;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;


import com.owoa.calendify.R;
import com.owoa.calendify.category.read.CategoryReadPresenter;

import android.widget.ListView;

import com.owoa.calendify.schedule.create.ScheduleCreateActivity;

public class ScheduleReadActivity extends AppCompatActivity {
    ScheduleReadActivity activity;
    CategoryReadPresenter categoryReadPresenter;
    String[] categories;
    ListView scheduleListView;
    String uid;
    private DrawerLayout drawerLayout;
    private View drawerView;



    public String getUid() {
        return uid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_read);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.drawerView);
        drawerLayout.setDrawerListener(listener);

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

        ImageView  scheduleNavigationButton = (ImageView) findViewById(R.id.create_navigation_button);
        scheduleNavigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(drawerView);
            }

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

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {

        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };
}