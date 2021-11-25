package com.owoa.calendify.friend.read;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.owoa.calendify.R;
import com.owoa.calendify.friend.create.FriendCreateActivity;
import com.owoa.calendify.schedule.read.ScheduleReadActivity;


public class FriendReadActivity extends AppCompatActivity {
    TextView friend_ID;
    FriendCreateActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        friend_ID = (TextView)findViewById(R.id.friend_ID);

        ImageView friendListButton = findViewById(R.id.friend_add_button);
        friendListButton.setOnClickListener( view -> {
            Intent intent = new Intent(FriendReadActivity.this, FriendCreateActivity.class);
            startActivityForResult(intent,1);
        });
    }


    //friend_ID.setText(result);




}
