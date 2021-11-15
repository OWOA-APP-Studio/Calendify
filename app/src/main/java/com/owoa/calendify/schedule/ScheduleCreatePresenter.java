package com.owoa.calendify.schedule;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
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
