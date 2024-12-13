package com.example.techfreelancers.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techfreelancers.R;
import com.example.techfreelancers.api.model.DictValue;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridCategoriesAdapter extends RecyclerView.Adapter<GridCategoriesAdapter.CategoryViewHolder> {

    private List<DictValue> categories;
    private GridCategoriesAdapter.OnItemClickListener listener;

    public GridCategoriesAdapter(List<DictValue> categories) {
        this.categories = categories;
    }

    // Interface for item click events
    public interface OnItemClickListener {
        void onItemClick(DictValue category);
    }

    public void setOnItemClickListener(GridCategoriesAdapter.OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_navcategory, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        DictValue navCategory = categories.get(position);
        holder.bind(navCategory);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(navCategory);
            }
        });
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

        void bind(DictValue navCategory) {
//            iconView.setImageResource(navCategory.getNote());
            Picasso.get()
                    .load(navCategory.getNote())
                    .placeholder(R.drawable.log) // Optional placeholder image
                    .error(R.drawable.error) // Optional error image
                    .into(iconView);
            nameView.setText(navCategory.getDictValueName());
        }
    }
}
