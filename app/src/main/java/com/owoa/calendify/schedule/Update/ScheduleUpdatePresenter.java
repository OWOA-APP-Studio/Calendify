package com.owoa.calendify.schedule.Update;

import com.owoa.calendify.loading.LoadingData;
import com.owoa.calendify.schedule.Contract;
import com.owoa.calendify.schedule.ScheduleCreateModel;
import com.owoa.calendify.schedule.ScheduleCreatePresenter;

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
