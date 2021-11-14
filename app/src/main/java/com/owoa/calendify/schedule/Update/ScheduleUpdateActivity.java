package com.owoa.calendify.schedule.Update;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.owoa.calendify.R;
import com.owoa.calendify.schedule.Contract;
import com.owoa.calendify.schedule.ScheduleCreatePresenter;

public class ScheduleUpdateActivity extends AppCompatActivity {

    private EditText sample_Name , sample_detail, sample_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_update);

        sample_Name = (EditText) findViewById(R.id.sample_Name);

    }
}

