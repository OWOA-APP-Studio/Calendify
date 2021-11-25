package com.owoa.calendify.friend.create;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.owoa.calendify.R;
import com.owoa.calendify.schedule.update.ScheduleUpdatePresenter;

public class FriendCreateActivity extends Activity {
    FriendCreatePresenter presenter;
    EditText friend_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FriendCreatePresenter(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_friend_add);


        friend_ID = (EditText)findViewById(R.id.friend_ID);

    }

    //확인 버튼 클릭

    public void mOnClose(View v){

        Intent resultIntent = new Intent();
        resultIntent.putExtra("friend_ID", friend_ID.getText().toString());
        setResult(RESULT_OK, resultIntent);
        finish();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        return;
    }

}
