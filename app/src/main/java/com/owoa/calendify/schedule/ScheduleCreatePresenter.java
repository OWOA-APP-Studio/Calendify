package com.owoa.calendify.schedule;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.owoa.calendify.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleCreatePresenter extends AppCompatActivity implements Contract.Presenter {
    Contract.View view;
    Contract.Presenter Presenter;
    ScheduleCreateModel scheduleCreateModel;

//    SimpleDateFormat mFormat_year = new SimpleDateFormat("yyyy");
//    SimpleDateFormat mFormat_month = new SimpleDateFormat("MM");
//    SimpleDateFormat mFormat_day = new SimpleDateFormat("dd");
//
//    long now = System.currentTimeMillis();
//    Date date = new Date(now);
//
//    private int year = Integer.parseInt(mFormat_year.format(date));
//    private int month = Integer.parseInt(mFormat_month.format(date));
//    private int day = Integer.parseInt(mFormat_day.format(date));
//
//    private int week ,y=0, m=0, d=0, h=0, mi=0;
//    private TextView edittext_date;

    public ScheduleCreatePresenter(Contract.View view){
        this.view = view;                   //Activty View정보 가져와 통신
        scheduleCreateModel = new ScheduleCreateModel(this);    //Model 객체 생성
    }

    //Presenter를 상속하고 addNum 구현
    @Override
    public void get(String name, String detail, String category,String repeat ,int week,String date,String time, String location) {
        view.showResult(name, detail, category,repeat,week,date,time,location);
        scheduleCreateModel.save(name,detail,category,repeat,week,date,time,location);
    }
}
