package com.owoa.calendify.friend.read;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.owoa.calendify.R;
import com.owoa.calendify.friend.create.FriendCreateActivity;
import com.owoa.calendify.schedule.read.ScheduleReadActivity;

public class FriendReadActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        ImageView friendListButton = findViewById(R.id.friend_add_button);
        friendListButton.setOnClickListener( view -> {
            Intent intent1 = new Intent(FriendReadActivity.this, FriendCreateActivity.class);
            startActivity(intent1);
        });
    }
}
