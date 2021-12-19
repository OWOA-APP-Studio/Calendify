package com.owoa.calendify.schedule.update;

import com.owoa.calendify.schedule.create.Contract;
import com.owoa.calendify.server.ServerData;

public class ScheduleUpdateModel extends ServerData {
    final static String REQUEST_SCHEDULE_UPDATE_URL = URL + "schedule/update.php";
    Contract.Presenter presenter;

    public ScheduleUpdateModel(Contract.Presenter presenter){
        this.presenter = presenter;
    }

    public String schedule_name;
    public String schedule_detail;
    public String schedule_category;
    public String schedule_repeat;
    public int schedule_week;
    public String schedule_date;
    public String schedule_time;
    public String schedule_location;

    public void save(String name, String detail, String category,String repeat, int week,String date,String time, String location){
        schedule_name = name;
        schedule_detail = detail;
        schedule_category = category;
        schedule_repeat = repeat;
        schedule_week = week;
        schedule_date = date;
        schedule_time = time;
        schedule_location = location;
    }
}
