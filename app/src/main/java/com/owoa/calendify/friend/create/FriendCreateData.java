package com.owoa.calendify.friend.create;

import com.owoa.calendify.server.ServerData;

public class FriendCreateData extends ServerData {
    static final String REQUEST_ADD_FRIEND_URL = URL + "friend/create.php";
    public String friendID;

    public FriendCreateData(FriendCreateActivity activity) {

    }

    public void save(String result) {
        friendID = result;
    }
}