package com.owoa.calendify.schedule;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.owoa.calendify.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleCreateActivity extends AppCompatActivity implements Contract.View {

    SimpleDateFormat mFormat_year = new SimpleDateFormat("yyyy");
    SimpleDateFormat mFormat_month = new SimpleDateFormat("MM");
    SimpleDateFormat mFormat_day = new SimpleDateFormat("dd");

    long now = System.currentTimeMillis();
    Date date = new Date(now);
    private int week ,y=0, m=0, d=0, h=0, mi=0;

    private int year = Integer.parseInt(mFormat_year.format(date));
    private int month = Integer.parseInt(mFormat_month.format(date));
    private int day = Integer.parseInt(mFormat_day.format(date));

    private Spinner spinner;
    private TextView tv_result;
    private TextView tv_week;
    private TextView edittext_date;
    private Button add_button, date_button, time_button;
    private RadioButton radioButton_day,radioButton_week;
    private RadioGroup radioGroup;
    private EditText sample_Name , sample_detail;
    private Contract.Presenter ScheduleCreatePresenter;
    private String repeat;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_create);
        ScheduleCreatePresenter = new ScheduleCreatePresenter((Contract.View) this);
        spinner = (Spinner) findViewById(R.id.spinner);
        tv_result = (TextView) findViewById(R.id.tv_result);
        add_button = (Button) findViewById(R.id.add_button);
        date_button = (Button) findViewById(R.id.date_button);
        time_button = (Button) findViewById(R.id.time_button);
        sample_Name = (EditText) findViewById(R.id.sample_Name);
        sample_detail = (EditText) findViewById(R.id.sample_Detail);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton_day = findViewById(R.id.radioButton_day);
        radioButton_week = findViewById(R.id.radioButton_week);
        edittext_date = (TextView) findViewById(R.id.sample_date);
        tv_week = (TextView) findViewById(R.id.tv_week);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);
        init();
    }


    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

            if(i == R.id.radioButton_day){
                repeat = radioButton_day.getText().toString();
                week = 0;
            } else if(i == R.id.radioButton_week){
                week = Integer.parseInt(tv_week.getText().toString());
            }
        }
    };


    protected void init() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv_result.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : click event
                String name = sample_Name.getText().toString();
                String detail = sample_detail.getText().toString();
                String category = tv_result.getText().toString();
                ScheduleCreatePresenter.get(name,detail,category,week);
                ScheduleCreateModel.saveData(tv_result);
            }
        });

        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate();

            }
        });

        time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTime();
            }
        });


    }

    void showDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                y = year;
                m = month+1;
                d = dayOfMonth;
            }
        },year, month-1, day);
        edittext_date.setText(y+m+d);
        datePickerDialog.setMessage("날짜");
        datePickerDialog.show();

    }

    void showTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                h = hourOfDay;
                mi = minute;

            }
        }, 21, 12, true);

        timePickerDialog.setMessage("시간");
        timePickerDialog.show();
    }

    @Override
    public void showResult(String name, String detail, String category ,int week) {
        Toast.makeText(getApplicationContext(),("일정 추가 완료" + "\n"+ name + "\n" + detail + "\n" + category + "\n" + week),Toast.LENGTH_LONG).show();
//        ((TextView)findViewById(R.id.title)).setText(answer + num2 + num3);
    }

}