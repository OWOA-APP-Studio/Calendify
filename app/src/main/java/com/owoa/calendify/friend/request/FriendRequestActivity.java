package com.owoa.calendify.friend.request;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.owoa.calendify.R;
import com.owoa.calendify.category.read.CategoryReadPresenter;
import com.owoa.calendify.friend.create.FriendCreateActivity;

public class FriendRequestActivity extends AppCompatActivity {

    ListView friend_request_List;
    RecyclerView friendCategoryView;
    CategoryReadPresenter categoryPresenter;
    FriendRequestPresenter presenter;
    String uid;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);
        activity = this;
        presenter = new FriendRequestPresenter(activity);
        friend_request_List = findViewById(R.id.friend_request_list);

        categoryPresenter = new CategoryReadPresenter(uid, activity);
        categoryPresenter.setRecyclerView(friendCategoryView);

        presenter.setFriendList(friend_request_List);
        presenter.loadFriends();
    }
}
