package com.example.techfreelancers.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.techfreelancers.R;
import com.example.techfreelancers.api.model.DictValue;

import java.util.List;


public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.ViewHolder> {

    private List<DictValue> categoryList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryName;

        public ViewHolder(View view) {
            super(view);
            categoryName = view.findViewById(R.id.categoryName);
        }
    }

    public categoryAdapter(List<DictValue> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public categoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DictValue category = categoryList.get(position);
        holder.categoryName.setText(category.getDictValueName());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
