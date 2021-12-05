package com.owoa.calendify.share.update;

import com.owoa.calendify.server.ServerData;

import org.json.JSONException;
import org.json.JSONObject;

public class ShareUpdateData extends ServerData {
    final static String REQUEST_SHARE_CATEGORY_READ_URL = URL + "share/read.php";
    final static String REQUEST_SHARE_CATEGORY_CREATE_URL = URL + "share/create.php";
    final static String REQUEST_SHARE_CATEGORY_DELETE_URL = URL + "share/delete.php";

    String friend_request_uid;
    String friend_category_uid;

    public ShareUpdateData(String friend_request_uid) {
        this.friend_request_uid = friend_request_uid;
    }

    public ShareUpdateData(JSONObject jsonObject) {
        try {
            if (jsonObject.length() != 0) {
                friend_request_uid = jsonObject.getString("frd_req_uid");
                friend_category_uid = jsonObject.getString("frd_ctg_id");
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getFriend_request_uid() {
        return friend_request_uid;
    }

    public String getFriend_category_uid() {
        return friend_category_uid;
    }
}
