package com.example.techfreelancers.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techfreelancers.R;
import com.example.techfreelancers.models.HotCategory;

import java.util.List;

public class HotCategoriesAdapter extends RecyclerView.Adapter<HotCategoriesAdapter.HotCategoryViewHolder> {

    private List<HotCategory> hotCategories;

    public HotCategoriesAdapter(List<HotCategory> hotCategories) {
        this.hotCategories = hotCategories;
    }

    @NonNull
    @Override
    public HotCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_hotcategory, parent, false);
        return new HotCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotCategoryViewHolder holder, int position) {
        HotCategory hotCategory = hotCategories.get(position);
        holder.bind(hotCategory);
    }

    @Override
    public int getItemCount() {
        return hotCategories.size();
    }

    static class HotCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;

        HotCategoryViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.hot_category_name);
        }

        void bind(HotCategory hotCategory) {
            nameView.setText(hotCategory.getName());
        }
    }
}
