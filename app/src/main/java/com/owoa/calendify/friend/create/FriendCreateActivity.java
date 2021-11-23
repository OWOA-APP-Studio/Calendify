package com.owoa.calendify.friend.create;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.owoa.calendify.R;

public class FriendCreateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_friend_add);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if( event.getAction() == MotionEvent.ACTION_OUTSIDE ) {
            return false;
        }
        return true;
    }

}
