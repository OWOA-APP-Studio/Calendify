package com.owoa.calendify.friend.create;

import com.owoa.calendify.schedule.update.ScheduleUpdateActivity;

public class FriendCreateData {

    public String friendID;

    public FriendCreateData(FriendCreateActivity activity) {

    }

    public void save(String result){
        friendID = result;
    }


}
