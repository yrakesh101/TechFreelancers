package com.example.techfreelancers.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.techfreelancers.R;
import com.example.techfreelancers.models.NavCategory;

public class GridCategoriesAdapter extends RecyclerView.Adapter<GridCategoriesAdapter.CategoryViewHolder> {

    private List<NavCategory> categories;

    public GridCategoriesAdapter(List<NavCategory> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_navcategory, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        NavCategory navCategory = categories.get(position);
        holder.bind(navCategory);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView iconView;
        TextView nameView;

        CategoryViewHolder(View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.category_icon);
            nameView = itemView.findViewById(R.id.category_name);
        }

        void bind(NavCategory navCategory) {
            iconView.setImageResource(navCategory.getIconResourceId());
            nameView.setText(navCategory.getName());
        }
    }
}
