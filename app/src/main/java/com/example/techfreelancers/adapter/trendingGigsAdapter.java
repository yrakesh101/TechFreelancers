package com.example.techfreelancers.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.techfreelancers.R;
import com.example.techfreelancers.model.trendingGigs;

import java.util.List;

public class trendingGigsAdapter extends RecyclerView.Adapter<trendingGigsAdapter.ViewHolder> {

    private List<trendingGigs> trendingGigsList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView projectTitle;
        public TextView cost;
        public TextView timeSpan;
        public TextView descriptionDetails;
        public ImageView profilePic;

        public ViewHolder(View view) {
            super(view);
            projectTitle = view.findViewById(R.id.projectTitle);
            cost = view.findViewById(R.id.cost);
            timeSpan = view.findViewById(R.id.timeSpan);
            descriptionDetails = view.findViewById(R.id.descriptionDetails);
            profilePic = view.findViewById(R.id.profilePic);
        }
    }

    public trendingGigsAdapter(List<trendingGigs> trendingGigsList) {
        this.trendingGigsList = trendingGigsList;
    }

    @Override
    public trendingGigsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_trending, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        trendingGigs gig = trendingGigsList.get(position);
        holder.projectTitle.setText(gig.getProjectTitle());
        holder.cost.setText(gig.getCost());
        holder.timeSpan.setText(gig.getTimeSpan());
        holder.descriptionDetails.setText(gig.getDescriptionDetails());
        // Set profile picture if available
        // holder.profilePic.setImageResource(gig.getProfilePicResource());
    }

    @Override
    public int getItemCount() {
        return trendingGigsList.size();
    }
}
