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

    public String[] getCategories() {
        return categoryArray;
    }

    public CategoryReadAdapter(String[] categoryArray, Activity activity, CategoryReadPresenter presenter) {
        this.categoryArray = categoryArray;
        this.activity = activity;
        this.presenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(new CategoryReadView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CategoryReadView) holder.itemView).displayItem(categoryArray[position], activity, position, presenter);
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
