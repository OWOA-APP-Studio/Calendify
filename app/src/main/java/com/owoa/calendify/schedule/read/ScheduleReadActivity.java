package com.owoa.calendify.schedule.read;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.owoa.calendify.R;
import com.owoa.calendify.category.read.CategoryListAdapter;

public class ScheduleReadActivity extends AppCompatActivity {
    ScheduleReadActivity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_read);

        activity = this;

        RecyclerView categoryView = (RecyclerView)findViewById(R.id.category_list);
        categoryView.setLayoutManager(new LinearLayoutManager(this));

        String[] categoryArray = {"Fitness", "Gaming", "Education","Animals", "Cars", "+"};

        categoryView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryView.setAdapter(new CategoryListAdapter(categoryArray,  activity));
    }
}