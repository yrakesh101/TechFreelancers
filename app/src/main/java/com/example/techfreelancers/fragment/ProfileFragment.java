package com.example.techfreelancers.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.techfreelancers.R;
import com.example.techfreelancers.activity.editProfileActivity;
import com.example.techfreelancers.databinding.FragmentProfileBinding;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private FragmentProfileBinding profileBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = profileBinding.getRoot();

        init();

        return root;
    }

    private void init() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LOGIN_USER_INFO", Context.MODE_PRIVATE);
        Integer userId = sharedPreferences.getInt("USER_ID", 0);
        if(0 != userId) {
            String imageUrl = "https://picsum.photos/id/"+ userId +"/200/200";
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.log) // Optional placeholder image
                    .error(R.drawable.error) // Optional error image
                    .into(profileBinding.profileImageView);
        }
        String username = sharedPreferences.getString("USER_NAME", "");
        if(!"".equals(username)) {
            profileBinding.usernameTextView.setText(username);
        }

        profileBinding.editIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == profileBinding.editIcon) {
            startActivity(new Intent(getContext(), editProfileActivity.class));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        profileBinding = null;
    }
}