package com.owoa.calendify.schedule.update;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.owoa.calendify.R;
import com.owoa.calendify.schedule.create.Contract;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleUpdateActivity extends AppCompatActivity {


    // 날짜 관련 변수 선언 //
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


    // 레이아웃 변수 //
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


    // 패턴 연결 //
    ScheduleUpdatePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_schedule_update);
        presenter = new ScheduleUpdatePresenter(this);

        spinner = (Spinner) findViewById(R.id.schedule_create_category_spinner);
//        tv_result = (TextView) findViewById(R.id.tv_result);
        add_button = (Button) findViewById(R.id.add_button);

        sample_Name = (EditText) findViewById(R.id.schedule_create_title);
        sample_location = (EditText) findViewById(R.id.schedule_create_location);

        radioGroup = (RadioGroup) findViewById(R.id.schedule_create_cycle_radioGroup);
        radioButton_day = findViewById(R.id.schedule_create_radio_day);
        radioButton_week = findViewById(R.id.schedule_create_radio_week);
//        edittext_date = (TextView) findViewById(R.id.sample_date);
//        edittext_time = (TextView) findViewById(R.id.sample_time);
        tv_week = (TextView) findViewById(R.id.schedule_create_cycle_repeat_times);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        start();



    }

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

            if(i == R.id.schedule_create_radio_day){
                repeat = radioButton_day.getText().toString();
                week = 0;
            } else if(i == R.id.schedule_create_radio_week){
                repeat = radioButton_week.getText().toString();
                week = Integer.parseInt(tv_week.getText().toString());
            }
        }
    };

    public void start(){

        //카테고리 선택//
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv_result.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }});

        // 수정 버튼 //
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : click event
                String name = sample_Name.getText().toString();
                String detail = sample_detail.getText().toString();
                String category = tv_result.getText().toString();
                String date = edittext_date.getText().toString();
                String time = edittext_time.getText().toString();
                String location = sample_location.getText().toString();
                presenter.get(name, detail, category, repeat, week, date, time, location);

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

    public void view(String name, String detail, String category, String repeat ,int week,String date,String time, String location) {
        Toast.makeText(getApplicationContext(),("일정 변경 완료" + "\n"
                + "일정명: " + name + "\n"
                + "상세내용: " + detail + "\n"
                +"카테고리 명: " + category + "\n"
                +"반복종류: "+ repeat + " " + week +" 주"+ "\n"
                +"날짜: " + date + "\n"
                +"시간: " + time + "\n"
                +"장소: " + location),Toast.LENGTH_LONG).show();
    }
}

