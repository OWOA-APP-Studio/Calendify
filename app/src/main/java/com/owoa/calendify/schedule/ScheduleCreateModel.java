package com.owoa.calendify.schedule;

import com.owoa.calendify.schedule.Update.ScheduleUpdateData;

public class ScheduleCreateModel {
    Contract.Presenter presenter;
    ScheduleUpdateData data;
    public ScheduleCreateModel(Contract.Presenter presenter){
        this.presenter = presenter;

    }
    public String name1;


    public void save(String name, String detail, String category,String repeat, int week,String date,String time, String location){
        name1 = name;
        detail = detail;
        category = category;
        repeat = repeat;
        week = week;
        date = date;
        time = time;
        location = location;
    };






}
