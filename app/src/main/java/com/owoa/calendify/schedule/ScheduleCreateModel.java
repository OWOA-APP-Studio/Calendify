package com.owoa.calendify.schedule;

public class ScheduleCreateModel {
    Contract.Presenter presenter;
    public ScheduleCreateModel(Contract.Presenter presenter){
        this.presenter = presenter;
    }


    public void save(String name, String detail, String category,String repeat, int week,String date,String time){
        name = name;
        detail = detail;
        category = category;
        repeat = repeat;
        week = week;
        date = date;
        time = time;
    };




}
