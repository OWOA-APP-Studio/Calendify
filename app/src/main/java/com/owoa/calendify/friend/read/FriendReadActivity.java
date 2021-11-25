package com.owoa.calendify.friend.read;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.owoa.calendify.R;
import com.owoa.calendify.category.read.CategoryReadPresenter;
import com.owoa.calendify.friend.create.FriendCreateActivity;

//class Code {
//    public static int requestCode = 100;
//    public static int resultCode = 1;
//}
public class FriendReadActivity extends AppCompatActivity {
    TextView txtResult;
    ListView friendList;

    CategoryReadPresenter categoryPresenter;

    String uid;
    Activity activity;

    RecyclerView friendCategoryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        txtResult = findViewById(R.id.txtResult);
        friendList = findViewById(R.id.friend_list);

        Intent intent = getIntent();
        uid = intent.getStringExtra(getString(R.string.uid));

        friendCategoryView = findViewById(R.id.friend_category_list);

        ImageView friendAddButton = findViewById(R.id.friend_add_button);
        friendAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(FriendReadActivity.this, FriendCreateActivity.class);
                intent2.putExtra(getString(R.string.uid), uid);
                startActivityForResult(intent2,1);
            }
        });
        categoryPresenter = new CategoryReadPresenter(uid, activity);
        categoryPresenter.setRecyclerView(friendCategoryView);
        categoryPresenter.loadFriendCategory();
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
