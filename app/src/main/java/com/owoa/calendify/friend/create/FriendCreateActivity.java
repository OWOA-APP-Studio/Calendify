package com.owoa.calendify.friend.create;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.owoa.calendify.R;

import com.owoa.calendify.friend.read.FriendReadActivity;

//class Code {
//    public static int requestCode = 100;
//    public static int resultCode = 1;
//}
public class FriendCreateActivity extends Activity {

    EditText txtText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_friend_add);

        //UI 객체생성
        txtText = (EditText)findViewById(R.id.txtText);

    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent resultIntent = new Intent();
        resultIntent.putExtra("name", txtText.getText().toString());
        setResult(RESULT_OK, resultIntent);
        //액티비티(팝업) 닫기
        finish();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
