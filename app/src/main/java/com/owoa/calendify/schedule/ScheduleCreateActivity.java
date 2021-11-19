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
import com.owoa.calendify.intro.IntroPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleCreateActivity extends AppCompatActivity implements Contract.View {
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    SimpleDateFormat mFormat_year = new SimpleDateFormat("yyyy");
    SimpleDateFormat mFormat_month = new SimpleDateFormat("MM");
    SimpleDateFormat mFormat_day = new SimpleDateFormat("dd");

    SimpleDateFormat mFormat_hour = new SimpleDateFormat("hh");
    SimpleDateFormat mFormat_minute = new SimpleDateFormat("mm");


    long now = System.currentTimeMillis();
    Date date = new Date(now);
    private int week ,y=0, m=0, d=0, h=0, mi=0;

    private int year = Integer.parseInt(mFormat_year.format(date));
    private int month = Integer.parseInt(mFormat_month.format(date));
    private int day = Integer.parseInt(mFormat_day.format(date));

    private int hour = Integer.parseInt(mFormat_hour.format(date));
    private int minute = Integer.parseInt(mFormat_minute.format(date));

    private Spinner spinner;
    private TextView tv_result;
    private TextView tv_week;
    private TextView edittext_date;
    private TextView edittext_time;
    private Button add_button, date_button, time_button;
    private RadioButton radioButton_day,radioButton_week;
    private RadioGroup radioGroup;
    private EditText sample_Name , sample_detail, sample_location;
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
        sample_location = (EditText) findViewById(R.id.sample_location);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton_day = findViewById(R.id.radioButton_day);
        radioButton_week = findViewById(R.id.radioButton_week);
        edittext_date = (TextView) findViewById(R.id.sample_date);
        edittext_time = (TextView) findViewById(R.id.sample_time);
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
                repeat = radioButton_week.getText().toString();
                week = Integer.parseInt(tv_week.getText().toString());
            }
        }
    };


    protected void init() {
        //카테고리 선택//
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv_result.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }});

        // 추가 버튼 //
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : click event
                String name = sample_Name.getText().toString();
                String detail = sample_detail.getText().toString();
                String category = tv_result.getText().toString();
                String date = edittext_date.getText().toString();
                String time= edittext_time.getText().toString();
                String location = sample_location.getText().toString();
                ScheduleCreatePresenter.get(name,detail,category,repeat,week,date,time,location);
                finish();
            }
        });

        // 날짜 선택 버튼 //
        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate();
            }
        });

        // 시간 선택 버튼 //
        time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTime();
            }
        });


    }

    // 날짜 //
    private void showDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                y = year;
                m = month+1;
                d = dayOfMonth;
                edittext_date.setText(String.format("%d년 %d월 %d일", y,m,d));
            }

        },year, month-1, day);

        datePickerDialog.setMessage("날짜");
        datePickerDialog.show();


    }

    // 시간 //
    private void showTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                h = hourOfDay;
                mi = minute;
                edittext_time.setText(String.format("%d시 %d분", h,mi));
            }
        }, hour, minute, true);

        timePickerDialog.setMessage("시간");
        timePickerDialog.show();
    }


    // Contract //
    @Override
    public void showResult(String name, String detail, String category, String repeat ,int week,String date,String time, String location) {
        Toast.makeText(getApplicationContext(),("일정 추가 완료" + "\n"
                + "일정명: " + name + "\n"
                + "상세내용: " + detail + "\n"
                +"카테고리 명: " + category + "\n"
                +"반복종류: "+ repeat + " " + week +" 주"+ "\n"
                +"날짜: " + date + "\n"
                +"시간: " + time + "\n"
                +"장소: " + location),Toast.LENGTH_LONG).show();
    }

}