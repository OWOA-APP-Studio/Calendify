package com.owoa.calendify.category.read;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.owoa.calendify.R;
import com.owoa.calendify.category.create.CategoryCreateActivity;

public class CategoryReadView extends FrameLayout {
    String uid;
    public CategoryReadView(Context context) {
        super(context);
        initializeView(context);
    }

    private void initializeView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_category, this);
    }

    public void displayItem(String text, Activity activity) {
        ((TextView)findViewById(R.id.categoryName)).setText(text);

        if(text.charAt(0) == '+') {
            TextView view = (findViewById(R.id.categoryName));
            view.setText("  +  ");
            view.setBackgroundColor(getContext().getColor(R.color.signature));

            view.setOnClickListener(v -> {
                String uid = activity.getIntent().getStringExtra(activity.getString(R.string.uid));

                Intent intent = new Intent(activity, CategoryCreateActivity.class);
                intent.putExtra(activity.getString(R.string.uid), uid);
                activity.startActivity(intent);
            });
        }
    }
}
