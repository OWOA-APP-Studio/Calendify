package com.owoa.calendify.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.owoa.calendify.R;

public class ScheduleCreateModel extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_create);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String name = bundle.getString("name");
        String detail = bundle.getString("detail");
        String category = bundle.getString("category");

        Toast.makeText(getApplicationContext(),(name + "\n" + detail + "\n" + category),Toast.LENGTH_LONG).show();

    }
}
