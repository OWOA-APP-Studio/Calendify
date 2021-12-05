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
import com.owoa.calendify.friend.request.FriendRequestActivity;

import java.util.ArrayList;


public class FriendReadActivity extends AppCompatActivity {
    TextView txtResult;
    ListView friendList;

    CategoryReadPresenter categoryPresenter;
    FriendReadPresenter presenter;

    String uid;
    Activity activity;
    ArrayList categories;

    RecyclerView friendCategoryView;
    TextView friend_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        activity = this;

        Intent intent = getIntent();
        uid = intent.getStringExtra(getString(R.string.uid));
        categories = (ArrayList) intent.getSerializableExtra("categories");

        presenter = new FriendReadPresenter(activity,uid);
        presenter.setCategories(categories);

        txtResult = findViewById(R.id.friend_ID);
        friendList = findViewById(R.id.friend_list);
        friend_ID = findViewById(R.id.friend_ID);

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

        //FriendRequestActivity 로 이동
        ImageView friendRequestButton = findViewById(R.id.friend_request_button);
        friendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(FriendReadActivity.this, FriendRequestActivity.class);
                intent3.putExtra(getString(R.string.uid), uid);
                startActivity(intent3);
            }
        });

        categoryPresenter = new CategoryReadPresenter(uid, activity);
        categoryPresenter.setRecyclerView(friendCategoryView);

        presenter.setFriendList(friendList);
        presenter.setCategories(categories);
        presenter.loadFriends();

    }
}
