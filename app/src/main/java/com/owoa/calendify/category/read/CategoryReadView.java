package com.owoa.calendify.category.read;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.owoa.calendify.R;
import com.owoa.calendify.category.create.CategoryCreateActivity;
import com.owoa.calendify.schedule.read.ScheduleReadActivity;

public class CategoryReadView extends FrameLayout {
    String uid;
    ScheduleReadActivity scheduleReadActivity;

    public void setScheduleReadActivity(ScheduleReadActivity scheduleReadActivity) {
        this.scheduleReadActivity = scheduleReadActivity;
    }

    public CategoryReadView(Context context) {
        super(context);
        initializeView(context);
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private void initializeView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_category, this);
    }

    public void displayItem(String text, Activity activity, int position, CategoryReadPresenter presenter, boolean isClickable) {
        ((TextView)findViewById(R.id.categoryName)).setText(text);

        if(text.charAt(0) == '+') {
            TextView view = (findViewById(R.id.categoryName));
            view.setText("  +  ");
            view.setBackgroundColor(getContext().getColor(R.color.signature));

            view.setOnClickListener(v -> {
                String uid = activity.getIntent().getStringExtra(activity.getString(R.string.uid));

                CategoryCreateActivity categoryCreateActivity = new CategoryCreateActivity();
                categoryCreateActivity.setScheduleReadActivity(scheduleReadActivity);

                Intent intent = new Intent(activity, categoryCreateActivity.getClass());
                intent.putExtra(activity.getString(R.string.uid), uid);

                activity.startActivity(intent);
            });
        }
        else {
            if(isClickable) {
                TextView view = findViewById(R.id.categoryName);
                view.setOnClickListener(v -> {
                    if(uid == null) {
                        presenter.initializeUserCategory(position);
                    }
                    else {
                        presenter.initializeFriendCategory(uid, position);
                    }
                });
            }
        }
    }
}
