package com.example.techfreelancers.adapter;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.techfreelancers.R;
import com.example.techfreelancers.api.model.TechProject;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class trendingGigsAdapter<Bitmap> extends RecyclerView.Adapter<trendingGigsAdapter.ViewHolder> {

    private List<TechProject> trendingGigsList;
    private trendingGigsAdapter.OnItemClickListener listener;

    // Interface for item click events
    public interface OnItemClickListener {
        void onItemClick(TechProject project);
    }

    public void setOnItemClickListener(trendingGigsAdapter.OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

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

    public trendingGigsAdapter(List<TechProject> trendingGigsList) {
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
        TechProject gig = trendingGigsList.get(position);
        holder.projectTitle.setText(gig.getProjectTitle());
        holder.cost.setText(gig.getProjectCost().toString());
        holder.timeSpan.setText(gig.getTimeSpan());
        holder.descriptionDetails.setText(gig.getProjectDetail());
        String imageUrl = "https://picsum.photos/id/"+ gig.getPublishUserId() +"/200/200";
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.log) // Optional placeholder image
                .error(R.drawable.error) // Optional error image
                .into(holder.profilePic);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(gig);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trendingGigsList.size();
    }

    public android.graphics.Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = (Bitmap) BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return (android.graphics.Bitmap) myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
}
