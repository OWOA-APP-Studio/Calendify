package com.owoa.calendify.friend.read;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.owoa.calendify.R;
import com.owoa.calendify.category.read.CategoryReadPresenter;
import com.owoa.calendify.friend.FriendModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class FriendReadAdapter extends BaseAdapter {
    FriendReadActivity activity;
    ArrayList<FriendModel> friendModels = new ArrayList<>();
    LayoutInflater layoutInflater;

    RecyclerView recyclerView;

    public FriendReadAdapter(Activity activity, JSONArray friends) {
        this.activity = (FriendReadActivity) activity;
        layoutInflater = LayoutInflater.from(activity.getApplicationContext());

        for (int i = 0; i < friends.length(); i++) {
            try {
                friendModels.add(new FriendModel(friends.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return friendModels.get(position);
    }

    @Override
    public int getCount() {
        return friendModels.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_friend, null);

        TextView friendName = view.findViewById(R.id.item_friend_name);
        friendName.setText(friendModels.get(position).getTargetUid());

        recyclerView = view.findViewById(R.id.friend_category_list);

        loadFriendCategoryList(position);

        return view;
    }

    public void loadFriendCategoryList(int position) {
        String uid = activity.uid;
        String friendUid = friendModels.get(position).getTargetUid();

        CategoryReadPresenter categoryReadPresenter = new CategoryReadPresenter(uid, activity);
        categoryReadPresenter.setRecyclerView(recyclerView);
        categoryReadPresenter.loadFriendCategory(friendUid);
    }

}
