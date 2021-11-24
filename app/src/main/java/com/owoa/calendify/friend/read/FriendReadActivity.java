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

//class Code {
//    public static int requestCode = 100;
//    public static int resultCode = 1;
//}
public class FriendReadActivity extends AppCompatActivity {
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        txtResult = (TextView)findViewById(R.id.txtResult);

        ImageView friendListButton = findViewById(R.id.friend_add_button);
        friendListButton.setOnClickListener( view -> {
            Intent intent = new Intent(FriendReadActivity.this, FriendCreateActivity.class);
            startActivityForResult(intent,1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                String result = data.getStringExtra("name");
                txtResult.setText(result);
            }
        }
    }

}
