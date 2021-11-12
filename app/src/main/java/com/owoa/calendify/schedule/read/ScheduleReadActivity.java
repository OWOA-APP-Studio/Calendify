package com.owoa.calendify.schedule.read;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import com.owoa.calendify.R;
import com.owoa.calendify.schedule.ScheduleCreateActivity;

public class ScheduleReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_read);
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