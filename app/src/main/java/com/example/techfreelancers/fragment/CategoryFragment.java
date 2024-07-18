package com.example.techfreelancers.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techfreelancers.activity.loginActivity;
import com.example.techfreelancers.activity.searchResultActivity;
import com.example.techfreelancers.adapter.GridCategoriesAdapter;
import com.example.techfreelancers.adapter.HotCategoriesAdapter;
import com.example.techfreelancers.api.DictApi;
import com.example.techfreelancers.api.ResponseModel;
import com.example.techfreelancers.api.model.DictValue;
import com.example.techfreelancers.databinding.FragmentCategoryBinding;
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

public class CategoryFragment extends Fragment implements View.OnClickListener {

    private FragmentCategoryBinding categoryBinding;

    RecyclerView hotCategoriesRecyclerView;
    RecyclerView categoriesGridRecyclerView;

    private Integer defaultCategoryId = 2;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        categoryBinding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = categoryBinding.getRoot();

        hotCategoriesRecyclerView = categoryBinding.hotCategoriesRecyclerView;

        categoriesGridRecyclerView = categoryBinding.categoriesGridRecyclerView;
        categoriesGridRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        init();

        return root;
    }

    private void init() {
        categoryBinding.searchButton.setOnClickListener(this);
        // get recommend project data from server to render on UI
        fetchHotCategories();
        // get category data from server to render on UI
        fetchNavCategories(2);
    }

    @Override
    public void onClick(View v) {
        if(v == categoryBinding.searchButton) {
            Intent intent = new Intent(getContext(), searchResultActivity.class);
            String searchText = categoryBinding.searchBar.getText().toString().trim();
            if (!"".equals(searchText) && searchText.length() > 0) {
                intent.putExtra("SEARCHTEXT", searchText);
            }
            startActivity(intent);
        }
    }

    private void fetchHotCategories() {
        Retrofit retrofit = RetrofitClient.getInstance(getContext());
        // show progress dialog
        ProgressDialog progressDialog = new ProgressDialog(getContext(), 1);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<ResponseModel<List<DictValue>>> call = retrofit.create(DictApi.class).queryDictValues(1);
        call.enqueue(new Callback<ResponseModel<List<DictValue>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<DictValue>>> call, Response<ResponseModel<List<DictValue>>> response) {
                // dissmiss the progress dialog
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getSuccess() && responseModel.getStatus() == 200) {
                        List<DictValue> leftCategories = (List<DictValue>) responseModel.getData();
                        HotCategoriesAdapter hotAdapter = new HotCategoriesAdapter(leftCategories);
                        hotAdapter.setOnItemClickListener(new HotCategoriesAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(DictValue category) {
                                Toast.makeText(getContext(), "Clicked: " + category.getNote(), Toast.LENGTH_SHORT).show();
                                Integer categoryId = Integer.valueOf(category.getNote());
                                defaultCategoryId = categoryId;
                                fetchNavCategories(categoryId);
                            }
                        });
                        hotCategoriesRecyclerView.setAdapter(hotAdapter);
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
                // dissmiss the progress dialog
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchNavCategories(Integer categoryId) {
        Retrofit retrofit = RetrofitClient.getInstance(getContext());
        // show progress dialog
        ProgressDialog progressDialog = new ProgressDialog(getContext(), 1);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<ResponseModel<List<DictValue>>> call = retrofit.create(DictApi.class).queryDictValues(categoryId);
        call.enqueue(new Callback<ResponseModel<List<DictValue>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<DictValue>>> call, Response<ResponseModel<List<DictValue>>> response) {
                // dissmiss the progress dialog
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getSuccess() && responseModel.getStatus() == 200) {
                        List<DictValue> categories = (List<DictValue>) responseModel.getData();
                        GridCategoriesAdapter adapter = new GridCategoriesAdapter(categories);
                        adapter.setOnItemClickListener(new GridCategoriesAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(DictValue rightCategory) {
                                Intent intent = new Intent(getContext(), searchResultActivity.class);
                                intent.putExtra("CATEGORYID", defaultCategoryId);
                                startActivity(intent);
                            }
                        });
                        categoriesGridRecyclerView.setAdapter(adapter);
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
                // dissmiss the progress dialog
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        categoryBinding = null;
    }
}