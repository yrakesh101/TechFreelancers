package com.example.techfreelancers.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techfreelancers.R;
import com.example.techfreelancers.adapter.MessagesAdapter;
import com.example.techfreelancers.databinding.FragmentMessageBinding;
import com.example.techfreelancers.models.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {

    private FragmentMessageBinding messageBinding;
    private RecyclerView recyclerView;
    private MessagesAdapter adapter;
    private List<Message> messageList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        messageBinding = FragmentMessageBinding.inflate(inflater, container, false);
        View root = messageBinding.getRoot();

        // Initialize RecyclerView and adapter
        initRecyclerView();

        return root;
    }

    private void initRecyclerView() {
        recyclerView = messageBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize message list and add sample data
        messageList = new ArrayList<>();
        messageList.add(new Message("User 1", "01/01/2024", "Hello", R.drawable.user));
        messageList.add(new Message("User 2", "02/01/2024", "Hi there", R.drawable.user));
        // Add more sample data as needed...

        adapter = new MessagesAdapter(messageList);
        recyclerView.setAdapter(adapter);

        // Set item click listener
        adapter.setOnItemClickListener(new MessagesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Message message) {
            }
        });
    }
}
