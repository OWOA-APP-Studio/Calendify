package com.owoa.calendify.friend.request;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.owoa.calendify.R;

public class FriendRequestActivity extends AppCompatActivity {

    FriendRequestPresenter presenter;
    ListView requestFriendListView;
    TextView request;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        Intent intent = getIntent();
        String uid = intent.getStringExtra(getString(R.string.uid));

        requestFriendListView = findViewById(R.id.friend_request_list);

        presenter = new FriendRequestPresenter(this, uid);
        presenter.setListView(requestFriendListView);
        presenter.loadRequestFriends();

        ImageView create_schedule_button = findViewById(R.id.create_schedule_button);
        create_schedule_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.setText(FriendRequestActivity.this.uid);
            }
        });
    }
}
