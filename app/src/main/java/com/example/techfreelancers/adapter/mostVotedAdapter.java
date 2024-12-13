package com.example.techfreelancers.adapter;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techfreelancers.R;
import com.example.techfreelancers.api.model.TechProject;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
public class mostVotedAdapter extends RecyclerView.Adapter<mostVotedAdapter.ViewHolder> {

    private List<TechProject> mostVotedGigsList;
    private mostVotedAdapter.OnItemClickListener listener;

    // Interface for item click events
    public interface OnItemClickListener {
        void onUpvoteClick(TechProject project);
        void onDownvoteClick(TechProject project);
    }

    public void setOnItemClickListener(mostVotedAdapter.OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView projectId;
        public TextView projectTitle;
        public TextView cost;
        public TextView timeSpan;
        public TextView descriptionDetails;
        public ImageView profilePic;
        public AppCompatButton upvoteButton;
        public TextView voteCount;
        public AppCompatButton downvoteButton;

        public ViewHolder(View view) {
            super(view);
            projectId = view.findViewById(R.id.projectId);
            projectTitle = view.findViewById(R.id.projectTitle);
            cost = view.findViewById(R.id.cost);
            timeSpan = view.findViewById(R.id.timeSpan);
            descriptionDetails = view.findViewById(R.id.descriptionDetails);
            profilePic = view.findViewById(R.id.profilePic);
            upvoteButton = view.findViewById(R.id.upvoteButton);
            voteCount = view.findViewById(R.id.voteCount);
            downvoteButton = view.findViewById(R.id.downvoteButton);
        }

    }

    public mostVotedAdapter(List<TechProject> mostVotedGigsList) {
        this.mostVotedGigsList = mostVotedGigsList;
    }

    @Override
    public mostVotedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_mostvoted, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TechProject gig = mostVotedGigsList.get(position);
        holder.projectId.setText(gig.getProjectId().toString());
        holder.projectTitle.setText(gig.getProjectTitle());
        holder.cost.setText(gig.getProjectCost().toString());
        holder.timeSpan.setText(gig.getTimeSpan());
        holder.descriptionDetails.setText(gig.getProjectDetail());
        holder.voteCount.setText(gig.getProjectVote().toString());
        String imageUrl = "https://picsum.photos/id/"+ gig.getPublishUserId() +"/200/200";
        // Set profile picture if available
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.log) // Optional placeholder image
                .error(R.drawable.error) // Optional error image
                .into(holder.profilePic);
        holder.upvoteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUpvoteClick(gig);
            }
        });
        holder.downvoteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDownvoteClick(gig);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mostVotedGigsList.size();
    }

    public android.graphics.Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            android.graphics.Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }
}
