package com.example.techfreelancers.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techfreelancers.R;
import com.example.techfreelancers.activity.gigDetailsActivity;
import com.example.techfreelancers.activity.loginActivity;
import com.example.techfreelancers.activity.mostVotedActivity;
import com.example.techfreelancers.adapter.categoryAdapter;
import com.example.techfreelancers.adapter.trendingGigsAdapter;
import com.example.techfreelancers.api.DictApi;
import com.example.techfreelancers.api.ProjectApi;
import com.example.techfreelancers.api.ResponseModel;
import com.example.techfreelancers.api.model.DictValue;
import com.example.techfreelancers.api.model.TechProject;
import com.example.techfreelancers.databinding.FragmentHomeBinding;
import com.example.techfreelancers.utils.RetrofitClient;
import com.example.techfreelancers.utils.SessionManager;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private trendingGigsAdapter adapter;
    private RecyclerView categoryRecyclerView;
    private categoryAdapter categoryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                actionBar.setCustomView(R.layout.custom_action_bar);
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF3700B3"))); // Set your color here
                TextView title = actionBar.getCustomView().findViewById(R.id.action_bar_title);
                title.setText("Home");
            }
        }

        recyclerView = binding.trendingsPostsView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        categoryRecyclerView = binding.categoryView;
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        TextView mostVotedTV = root.findViewById(R.id.mostVotedTV);

        // Set click listener
        mostVotedTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open mostVotedActivity
                Intent intent = new Intent(getContext(), mostVotedActivity.class);
                startActivity(intent);
            }
        });
        init();

        return root;
    }

    private void init() {
        // show progress dialog
        ProgressDialog progressDialog = new ProgressDialog(getContext(), 1);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        // get recommend project data from server to render on UI
        fetchRecommendProjects();
        // get category data from server to render on UI
        fetchCategories();
        // dissmiss the progress dialog
        progressDialog.dismiss();
    }

    /**
     * get recommend project data from server to render on UI
     */
    private void fetchRecommendProjects() {
        Retrofit retrofit = RetrofitClient.getInstance(getContext());
        Call<ResponseModel<List<TechProject>>> call = retrofit.create(ProjectApi.class).queryRecommendProject();
        call.enqueue(new Callback<ResponseModel<List<TechProject>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<TechProject>>> call, Response<ResponseModel<List<TechProject>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getSuccess() && responseModel.getStatus() == 200) {
                        List<TechProject> projects = (List<TechProject>) responseModel.getData();
                        adapter = new trendingGigsAdapter(projects);
                        adapter.setOnItemClickListener(new trendingGigsAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(TechProject project) {
                                Intent intent = new Intent(getContext(), gigDetailsActivity.class);
                                intent.putExtra("projectId", project.getProjectId());
                                startActivity(intent);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getContext(), responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ResponseModel> converter = retrofit.responseBodyConverter(ResponseModel.class, new Annotation[0]);
                    ResponseModel errorModel = null;
                    try {
                        errorModel = converter.convert(response.errorBody());
                        Toast.makeText(getContext(), errorModel.getMessage(), Toast.LENGTH_SHORT).show();
                        if(401 == errorModel.getStatus()) {
                            SessionManager.clearUserSession(getContext());
                            startActivity(new Intent(getContext(), loginActivity.class));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<TechProject>>> call, Throwable t) {
                Toast.makeText(getContext(), "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * get category data from server to render on UI
     *
     * @return
     */
    private void fetchCategories() {
        Retrofit retrofit = RetrofitClient.getInstance(getContext());
        Call<ResponseModel<List<DictValue>>> call = retrofit.create(DictApi.class).queryDictValues(1);
        call.enqueue(new Callback<ResponseModel<List<DictValue>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<DictValue>>> call, Response<ResponseModel<List<DictValue>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getSuccess() && responseModel.getStatus() == 200) {
                        List<DictValue> categories = (List<DictValue>) responseModel.getData();
                        categoryAdapter = new categoryAdapter(categories);
                        categoryRecyclerView.setAdapter(categoryAdapter);
                    } else {
                        Toast.makeText(getContext(), responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ResponseModel> converter = retrofit.responseBodyConverter(ResponseModel.class, new Annotation[0]);
                    ResponseModel errorModel = null;
                    try {
                        errorModel = converter.convert(response.errorBody());
                        Toast.makeText(getContext(), errorModel.getMessage(), Toast.LENGTH_SHORT).show();
                        if(401 == errorModel.getStatus()) {
                            SessionManager.clearUserSession(getContext());
                            startActivity(new Intent(getContext(), loginActivity.class));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<DictValue>>> call, Throwable t) {
                Toast.makeText(getContext(), "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}