package com.example.techfreelancers.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.techfreelancers.activity.editProfileActivity;
import com.example.techfreelancers.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private FragmentProfileBinding profileBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = profileBinding.getRoot();

        init();

        return root;
    }

    private void init() {
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