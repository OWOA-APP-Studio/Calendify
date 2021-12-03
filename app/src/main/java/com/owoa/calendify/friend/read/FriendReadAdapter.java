package com.owoa.calendify.friend.read;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.owoa.calendify.R;
import com.owoa.calendify.category.read.CategoryReadPresenter;
import com.owoa.calendify.friend.FriendModel;
import com.owoa.calendify.friend.delete.FriendDeletePresenter;
import com.owoa.calendify.schedule.ScheduleModel;
import com.owoa.calendify.schedule.delete.ScheduleDeletePresenter;
import com.owoa.calendify.schedule.update.ScheduleUpdateActivity;
import com.owoa.calendify.share.update.ShareUpdateActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class FriendReadAdapter extends BaseAdapter {
    FriendReadActivity activity;
    ArrayList<FriendModel> friendModels = new ArrayList<>();
    LayoutInflater layoutInflater;

    RecyclerView recyclerView;

    String uid;

    public FriendReadAdapter(Activity activity, JSONArray friends, String uid) {
        this.activity = (FriendReadActivity) activity;
        this.uid = uid;

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

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "친구가 공유한 일정을 보여줍니다.", Toast.LENGTH_SHORT).show();
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final CharSequence[] select = {"공유 카테고리 수정", "친구 삭제"};

                AlertDialog.Builder oDialog = new AlertDialog.Builder(activity, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

                oDialog.setTitle("일정 관리");
                oDialog.setItems(select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(activity, ShareUpdateActivity.class);
                                intent.putExtra(activity.getString(R.string.uid), uid);
                                intent.putExtra("req_uid", friendModels.get(position).getRequestUid());
                                intent.putExtra("tg_uid", friendModels.get(position).getTargetUid());
                                activity.startActivity(intent);
                                break;
                            case 1:
                                FriendDeletePresenter presenter = new FriendDeletePresenter(activity, friendModels.get(position).getRequestUid(), friendModels.get(position).getTargetUid());
                                presenter.deleteFriend();
                        }

                    }
                });
                oDialog.show();
                return false;
            }
        });
        return view;
    }

    public void loadFriendCategoryList(int position) {
        String uid = activity.uid;
        String friendUid = friendModels.get(position).getTargetUid();

        CategoryReadPresenter categoryReadPresenter = new CategoryReadPresenter(uid, activity);
        categoryReadPresenter.setRecyclerView(recyclerView);
        categoryReadPresenter.loadFriendCategory();
    }

}
