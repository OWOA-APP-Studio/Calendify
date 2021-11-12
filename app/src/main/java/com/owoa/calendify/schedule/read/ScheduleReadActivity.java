package com.owoa.calendify.schedule.read;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.owoa.calendify.R;
import com.owoa.calendify.category.create.CategoryCreateActivity;
import com.owoa.calendify.category.read.CategoryAdapter;

public class ScheduleReadActivity extends AppCompatActivity {
    ScheduleReadActivity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_read);

        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        activity = this;

        TextView categoryTextView = findViewById(R.id.create_category);
        RecyclerView categoryView = (RecyclerView)findViewById(R.id.category_list);
        categoryView.setLayoutManager(new LinearLayoutManager(this));

        String[] categoryArray = {"Fitness", "Gaming", "Education","Animals", "Cars", "+"};

        categoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleReadActivity.this, CategoryCreateActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
        });

        categoryView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryView.setAdapter(new CategoryAdapter(categoryArray,  activity, uid));
    }
}