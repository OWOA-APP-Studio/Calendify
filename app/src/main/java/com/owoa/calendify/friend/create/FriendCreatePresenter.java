package com.owoa.calendify.friend.create;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.owoa.calendify.R;
import com.owoa.calendify.account.UserData;
import com.owoa.calendify.schedule.update.ScheduleUpdateActivity;
import com.owoa.calendify.schedule.update.ScheduleUpdateData;

public class FriendCreatePresenter extends AppCompatActivity {
    FriendCreateActivity activity;
    FriendCreateData data;
    UserData userData;
    String myID;


    public FriendCreatePresenter(FriendCreateActivity activity) {
        this.activity = activity;
        data = new FriendCreateData(activity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                String result = data.getStringExtra("friend_ID");
                save(result);
            }
        }
    }

    protected void save(String result){
        data.save(result);
    };


}
