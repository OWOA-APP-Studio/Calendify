package com.owoa.calendify.schedule.update;

public class ScheduleUpdateData {
    ScheduleUpdatePresenter presenter;

    public String schedule_name;
    public String schedule_detail;
    public String schedule_category;
    public String schedule_repeat;
    public int schedule_week;
    public String schedule_date;
    public String schedule_time;
    public String schedule_location;

    public ScheduleUpdateData(ScheduleUpdateActivity activity) {

    }

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
