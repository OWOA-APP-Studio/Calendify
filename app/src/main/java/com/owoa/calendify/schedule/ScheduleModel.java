package com.owoa.calendify.schedule;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ScheduleModel implements Serializable {
    private String memberUid;
    private String scheduleId;
    private String title;
    private String description;
    private String category;
    private String location;
    private String startTime;
    private String endTime;
    private String startDay;
    private String endDay;
    private String scheduleSelectType;

    public ScheduleModel(JSONObject jsonObject) {
        try {
            memberUid = jsonObject.getString("sch_mmb_uid");
            scheduleId = jsonObject.getString("sch_id");
            title = jsonObject.getString("sch_ttl");
            description = jsonObject.getString("sch_dsc");
            category = jsonObject.getString("sch_ctg");
            location = jsonObject.getString("sch_lct");
            startTime = jsonObject.getString("sch_stm");
            endTime = jsonObject.getString("sch_etm");
            startDay = jsonObject.getString("sch_sdy");
            endDay = jsonObject.getString("sch_edy");
            scheduleSelectType = jsonObject.getString("sch_slc_typ");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getMemberUid() {
        return memberUid;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getLocation() {
        return location;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartDay() {
        return startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }



    public String getScheduleSelectType() {
        return scheduleSelectType;
    }
}
