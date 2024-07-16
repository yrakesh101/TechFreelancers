package com.example.techfreelancers.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.techfreelancers.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        if (activity != null) {
//            ActionBar actionBar = activity.getSupportActionBar();
//            if (actionBar != null) {
//                actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//                actionBar.setCustomView(R.layout.custom_action_bar);
//                actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF3700B3"))); // Set your color here
//                TextView title = actionBar.getCustomView().findViewById(R.id.action_bar_title);
//                title.setText("Settings");
//            }
//        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}