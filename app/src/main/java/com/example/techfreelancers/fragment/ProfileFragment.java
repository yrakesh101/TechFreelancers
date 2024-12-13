package com.example.techfreelancers.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.techfreelancers.R;
import com.example.techfreelancers.activity.AcceptedActivity;
import com.example.techfreelancers.activity.LoadingActivity;
import com.example.techfreelancers.activity.PostedActivity;
import com.example.techfreelancers.activity.editProfileActivity;
import com.example.techfreelancers.activity.loginActivity;
import com.example.techfreelancers.databinding.FragmentProfileBinding;
import com.example.techfreelancers.utils.SessionManager;
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
        profileBinding.postProjectButton.setOnClickListener(this);
        profileBinding.acceptProjectButton.setOnClickListener(this);
        profileBinding.logOutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == profileBinding.editIcon) {
            startActivity(new Intent(getContext(), editProfileActivity.class));
        }
        else if(v == profileBinding.postProjectButton) {
            startActivity(new Intent(getContext(), PostedActivity.class));
        }
        else if(v == profileBinding.acceptProjectButton) {
            startActivity(new Intent(getContext(), AcceptedActivity.class));
        }
        else if(v == profileBinding.logOutButton) {

            // Create an AlertDialog builder
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            // Set the dialog title and message
            builder.setMessage("Do you really want to log out?");
            // Add a button to the dialog
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Handle the button click (if needed)

                    SessionManager.clearUserSession(getContext());
                    Toast.makeText(getContext(), "User log out.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), loginActivity.class));
                    dialog.dismiss(); // Close the dialog
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Handle the button click (if needed)
                    dialog.dismiss(); // Close the dialog
                }
            });
            // Create and show the AlertDialog
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        profileBinding = null;
    }
}