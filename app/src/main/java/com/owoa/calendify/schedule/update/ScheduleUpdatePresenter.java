package com.owoa.calendify.schedule.update;

public class ScheduleUpdatePresenter {
    ScheduleUpdateActivity activity;
    ScheduleUpdateData data;


    public ScheduleUpdatePresenter(ScheduleUpdateActivity activity) {
        this.activity = activity;
        data = new ScheduleUpdateData(activity);


    }

    public void get(String name, String detail, String category,String repeat ,int week,String date,String time, String location){
        activity.view(name, detail, category,repeat,week,date,time,location);
        data.save(name,detail,category,repeat,week,date,time,location);
    }





}
