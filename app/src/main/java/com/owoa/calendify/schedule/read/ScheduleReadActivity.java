package com.owoa.calendify.schedule.read;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
=======
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
>>>>>>> feature/SDU/001

import com.owoa.calendify.R;
import com.owoa.calendify.category.read.CategoryReadPresenter;
import android.view.View;
import android.widget.ImageView;
import com.owoa.calendify.schedule.ScheduleCreateActivity;
import com.owoa.calendify.schedule.Update.ScheduleUpdateActivity;

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
                Intent intent = new Intent(getApplicationContext(), ScheduleCreateActivity.class);
                startActivity(intent);
            }
        });


<<<<<<< HEAD

=======
>>>>>>> feature/SDU/001
    }
}