package com.example.techfreelancers.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techfreelancers.R;
import com.example.techfreelancers.activity.profileActivity;
import com.example.techfreelancers.activity.viewAllActivity;
import com.example.techfreelancers.adapter.categoryAdapter;
import com.example.techfreelancers.adapter.trendingGigsAdapter;
import com.example.techfreelancers.api.DictApi;
import com.example.techfreelancers.api.ProjectApi;
import com.example.techfreelancers.api.ResponseModel;
import com.example.techfreelancers.api.model.DictValue;
import com.example.techfreelancers.api.model.TechProject;
import com.example.techfreelancers.databinding.FragmentSearchBinding;
import com.example.techfreelancers.utils.RetrofitClient;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private RecyclerView recyclerView;
    private trendingGigsAdapter adapter;
    private RecyclerView categoryRecyclerView;
    private categoryAdapter categoryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.trendingsPostsView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}