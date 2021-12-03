package com.owoa.calendify.friend.request;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.owoa.calendify.R;
import com.owoa.calendify.friend.create.FriendCreateActivity;

public class FriendRequestActivity extends AppCompatActivity {

    TextView request;
    String Uid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        request = findViewById(R.id.friend_request_text);



        ImageView create_schedule_button = findViewById(R.id.create_schedule_button);
        create_schedule_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.setText(Uid);
            }
        });
    }




}
