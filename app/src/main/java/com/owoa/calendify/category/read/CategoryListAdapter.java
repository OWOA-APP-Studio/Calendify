package com.owoa.calendify.category.read;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryListAdapter extends RecyclerView.Adapter {

    private String[] categoryArray;
    private Activity activity;

    public CategoryListAdapter(String[] categoryArray, Activity activity) {
        this.categoryArray = categoryArray;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(new CategoryView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CategoryView)holder.itemView).displayItem(categoryArray[position], activity);
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
