package com.owoa.calendify.schedule.read;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.owoa.calendify.R;
import com.owoa.calendify.category.create.CategoryCreateActivity;

public class ScheduleReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_read);

        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");

        TextView categoryTextView = findViewById(R.id.create_category);

        categoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleReadActivity.this, CategoryCreateActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
        });
    }
}