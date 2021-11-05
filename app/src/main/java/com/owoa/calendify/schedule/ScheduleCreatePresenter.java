package com.owoa.calendify.schedule;

import android.app.TimePickerDialog;
import android.widget.TimePicker;

public class ScheduleCreatePresenter implements Contract.Presenter {
    Contract.View view;
    Contract.Presenter Presenter;
    ScheduleCreateModel scheduleCreateModel;



    public ScheduleCreatePresenter(Contract.View view){
        this.view = view;                   //Activty View정보 가져와 통신
        scheduleCreateModel = new ScheduleCreateModel(this);    //Model 객체 생성
    }

    //Presenter를 상속하고 addNum 구현
    @Override
    public void get(String name, String detail, String category,String repeat ,int week,String date,String time) {
        view.showResult(name, detail, category,repeat,week,date,time);
        scheduleCreateModel.save(name,detail,category,repeat,week,date,time);
    }



}
