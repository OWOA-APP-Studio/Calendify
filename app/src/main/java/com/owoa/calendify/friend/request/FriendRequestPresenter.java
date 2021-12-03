package com.owoa.calendify.friend.request;

import android.app.Activity;
import android.widget.ListView;

public class FriendRequestPresenter {
    Activity activity;
    String uid;
    ListView listView;

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public FriendRequestPresenter(Activity activity, String uid) {
        this.activity = activity;
        this.uid = uid;


    }

    public void loadRequestFriends() {
//        FriendRequestAdapter adapter = new FriendRequestAdapter(uid, activity);
//        listView.setAdapter(adapter);
    }

}
