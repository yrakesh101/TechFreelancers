package com.example.techfreelancers.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techfreelancers.R;
import com.example.techfreelancers.models.Message;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    private List<Message> messagesList;
    private MessagesAdapter.OnItemClickListener listener;

    // Interface for item click events
    public interface OnItemClickListener {
        void onItemClick(Message message);
    }

    public MessagesAdapter(List<Message> messagesList) {
        this.messagesList = messagesList;
    }

    // Method to set click listener from outside the adapter
    public void setOnItemClickListener(MessagesAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_messages, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messagesList.get(position);
        holder.username.setText(message.getUsername());
        holder.dateReceived.setText(message.getDateReceived());
        holder.lastMessage.setText(message.getLastMessage());
        holder.profilePic.setImageResource(message.getProfilePic());

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(message);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic;
        TextView username, dateReceived, lastMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profilePic);
            username = itemView.findViewById(R.id.usernames);
            dateReceived = itemView.findViewById(R.id.dateRecieved);
            lastMessage = itemView.findViewById(R.id.lastMessage);
        }
    }
}
