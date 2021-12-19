package com.owoa.calendify.category.read;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.owoa.calendify.category.read.CategoryReadView;

public class CategoryReadAdapter extends RecyclerView.Adapter {
    private String[] categoryArray;
    private Activity activity;
    private CategoryReadPresenter presenter;

    private boolean isClickable;

    private String uid;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPresenter(CategoryReadPresenter presenter) {
        this.presenter = presenter;
    }

    public CategoryReadAdapter(String[] categoryArray, Activity activity, CategoryReadPresenter presenter) {
        this.categoryArray = categoryArray;
        this.activity = activity;
        this.presenter = presenter;
        this.isClickable = true;
    }

    public CategoryReadAdapter(String[] categoryArray, Activity activity, String uid, boolean isClickable) {
        this.categoryArray = categoryArray;
        this.activity = activity;
        setUid(uid);
        this.isClickable = isClickable;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(new CategoryReadView(parent.getContext()));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CategoryReadView view = (CategoryReadView)holder.itemView;

        if (uid != null) {
            view.setUid(uid);
        }
        view.displayItem(categoryArray[position], activity, position, presenter, isClickable);
//        ((CategoryReadView) holder.itemView).displayItem(categoryArray[position], activity, position, presenter, isClickable);
    }

    @Override
    public int getItemCount() {
        return categoryArray.length;
    }

    private class CategoryViewHolder extends RecyclerView.ViewHolder {
        public CategoryViewHolder(View itemView) {
            super(itemView);
        }
    }
}
