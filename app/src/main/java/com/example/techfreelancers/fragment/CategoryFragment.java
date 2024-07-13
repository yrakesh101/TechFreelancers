package com.example.techfreelancers.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techfreelancers.R;
import com.example.techfreelancers.adapter.GridCategoriesAdapter;
import com.example.techfreelancers.adapter.HotCategoriesAdapter;
import com.example.techfreelancers.adapter.MessagesAdapter;
import com.example.techfreelancers.databinding.FragmentCategoryBinding;
import com.example.techfreelancers.models.HotCategory;
import com.example.techfreelancers.models.NavCategory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding categoryBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        categoryBinding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = categoryBinding.getRoot();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                actionBar.setCustomView(R.layout.custom_action_bar);
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF3700B3"))); // Set your color here
                TextView title = actionBar.getCustomView().findViewById(R.id.action_bar_title);
                title.setText("Category");
            }
        }

        RecyclerView hotCategoriesRecyclerView = categoryBinding.hotCategoriesRecyclerView;
        HotCategoriesAdapter hotAdapter = new HotCategoriesAdapter(getHotCategories());
        hotCategoriesRecyclerView.setAdapter(hotAdapter);

        RecyclerView categoriesGridRecyclerView = categoryBinding.categoriesGridRecyclerView;
        categoriesGridRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        GridCategoriesAdapter adapter = new GridCategoriesAdapter(getNavCategories());
        categoriesGridRecyclerView.setAdapter(adapter);

        return root;
    }

    private List<HotCategory> getHotCategories() {
        List<HotCategory> hotCategories = new ArrayList<>();
        hotCategories.add(new HotCategory("1", "MOBILE APP"));
        hotCategories.add(new HotCategory("1", "DESKTOP APP"));
        hotCategories.add(new HotCategory("1", "WEBSITE"));
        hotCategories.add(new HotCategory("1", "CLOUD DEV"));
        hotCategories.add(new HotCategory("1", "BLOCK CHAIN"));
        hotCategories.add(new HotCategory("1", "BLOG"));
        hotCategories.add(new HotCategory("1", "DASHBOARD"));
        hotCategories.add(new HotCategory("1", "WEBSITE"));
        hotCategories.add(new HotCategory("1", "CLOUD DEV"));
        hotCategories.add(new HotCategory("1", "BLOCK CHAIN"));
        hotCategories.add(new HotCategory("1", "MOBILE APP"));
        hotCategories.add(new HotCategory("1", "DESKTOP APP"));
        hotCategories.add(new HotCategory("1", "WEBSITE"));
        hotCategories.add(new HotCategory("1", "CLOUD DEV"));
        hotCategories.add(new HotCategory("1", "BLOCK CHAIN"));
        hotCategories.add(new HotCategory("1", "MOBILE APP"));
        hotCategories.add(new HotCategory("1", "DESKTOP APP"));
        hotCategories.add(new HotCategory("1", "WEBSITE"));
        hotCategories.add(new HotCategory("1", "CLOUD DEV"));
        hotCategories.add(new HotCategory("1", "BLOCK CHAIN"));
        hotCategories.add(new HotCategory("1", "MOBILE APP"));
        hotCategories.add(new HotCategory("1", "DESKTOP APP"));
        hotCategories.add(new HotCategory("1", "WEBSITE"));
        hotCategories.add(new HotCategory("1", "CLOUD DEV"));
        hotCategories.add(new HotCategory("1", "BLOCK CHAIN"));
        hotCategories.add(new HotCategory("1", "MOBILE APP"));
        hotCategories.add(new HotCategory("1", "DESKTOP APP"));
        hotCategories.add(new HotCategory("1", "WEBSITE"));
        hotCategories.add(new HotCategory("1", "CLOUD DEV"));
        hotCategories.add(new HotCategory("1", "BLOCK CHAIN"));
        // Add more hot categories as needed
        return hotCategories;
    }

    private List<NavCategory> getNavCategories() {
        List<NavCategory> categories = new ArrayList<>();
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        categories.add(new NavCategory("1", "IOS", R.drawable.log));
        categories.add(new NavCategory("2", "Android", R.drawable.log));
        categories.add(new NavCategory("3", "ReactNative", R.drawable.log));
        // Add more categories as needed
        return categories;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        categoryBinding = null;
    }
}