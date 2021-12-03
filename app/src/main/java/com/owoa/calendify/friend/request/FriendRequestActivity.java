package com.owoa.calendify.friend.request;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.owoa.calendify.R;
import com.owoa.calendify.friend.create.FriendCreateActivity;

public class FriendRequestActivity extends AppCompatActivity {

    FriendRequestPresenter presenter;
    String uid;
    ListView requestFriendListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        Intent intent = getIntent();
        String uid = intent.getStringExtra(getString(R.string.uid));

        requestFriendListView = findViewById(R.id.friend_request_list);

        presenter = new FriendRequestPresenter(this, uid);
        presenter.loadRequestFriends();
    }
}
