package com.example.techfreelancers.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techfreelancers.R;
import com.example.techfreelancers.api.model.DictValue;

import java.util.List;

public class HotCategoriesAdapter extends RecyclerView.Adapter<HotCategoriesAdapter.HotCategoryViewHolder> {

    private List<DictValue> hotCategories;
    private HotCategoriesAdapter.OnItemClickListener listener;

    private int selectedPosition = RecyclerView.NO_POSITION;

    // Interface for item click events
    public interface OnItemClickListener {
        void onItemClick(DictValue category);
    }

    public HotCategoriesAdapter(List<DictValue> hotCategories) {
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
        DictValue hotCategory = hotCategories.get(position);
        holder.bind(hotCategory);
        holder.itemView.setBackgroundColor(selectedPosition == position ?
                holder.itemView.getContext().getResources().getColor(android.R.color.holo_blue_light) :
                holder.itemView.getContext().getResources().getColor(android.R.color.transparent));
        holder.itemView.setOnClickListener(v -> {
            notifyItemChanged(selectedPosition);
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(selectedPosition);
            if (listener != null) {
                listener.onItemClick(hotCategory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotCategories.size();
    }

    public void setOnItemClickListener(HotCategoriesAdapter.OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    static class HotCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;

        HotCategoryViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.hot_category_name);
        }

        void bind(DictValue hotCategory) {
            nameView.setText(hotCategory.getDictValueName());
        }
    }
}
