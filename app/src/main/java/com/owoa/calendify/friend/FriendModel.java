package com.owoa.calendify.friend;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendModel {
    private String requestUid;
    private String targetUid;
    private String selectDateTime;


    public FriendModel(JSONObject object) {
        try {
            requestUid = object.getString("req_uid");
            targetUid = object.getString("tg_uid");
            selectDateTime = object.getString("slt_dt");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getRequestUid() {
        return requestUid;
    }

    public String getTargetUid() {
        return targetUid;
    }

    public String getSelectDateTime() {
        return selectDateTime;
    }
}
