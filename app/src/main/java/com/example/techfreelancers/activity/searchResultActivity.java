package com.example.techfreelancers.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techfreelancers.R;
import com.example.techfreelancers.adapter.SearchResultAdapter;
import com.example.techfreelancers.api.ProjectApi;
import com.example.techfreelancers.api.ResponseModel;
import com.example.techfreelancers.api.model.TechProject;
import com.example.techfreelancers.databinding.ActivitySearchResultBinding;
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


public class searchResultActivity extends AppCompatActivity {

    private ActivitySearchResultBinding searchResultBinding;
    private RecyclerView recyclerView;
    private SearchResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchResultBinding = ActivitySearchResultBinding.inflate(getLayoutInflater());
        setContentView(searchResultBinding.getRoot());

        recyclerView = searchResultBinding.mostVotedRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(searchResultBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            Drawable navIcon = searchResultBinding.toolbar.getNavigationIcon();
            if (navIcon != null) {
                @ColorInt int color = getResources().getColor(R.color.white);
                navIcon.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            }
        }
        init();
    }

    private void init() {
        // show progress dialog
        ProgressDialog progressDialog = new ProgressDialog(this, 1);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        // get recommend project data from server to render on UI
        fetchSearchProjects();
        // dissmiss the progress dialog
        progressDialog.dismiss();
    }

    /**
     * get recommend project data from server to render on UI
     */
    private void fetchSearchProjects() {
        Retrofit retrofit = RetrofitClient.getInstance(getApplicationContext());
        Call<ResponseModel<List<TechProject>>> call = retrofit.create(ProjectApi.class).queryRecommendProject();
        call.enqueue(new Callback<ResponseModel<List<TechProject>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<TechProject>>> call, Response<ResponseModel<List<TechProject>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getSuccess() && responseModel.getStatus() == 200) {
                        List<TechProject> projects = (List<TechProject>) responseModel.getData();
                        adapter = new SearchResultAdapter(projects);
                        adapter.setOnItemClickListener(new SearchResultAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(TechProject project) {
                                Intent intent = new Intent(getApplicationContext(), gigDetailsActivity.class);
                                intent.putExtra("projectId", project.getProjectId());
                                startActivity(intent);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getApplicationContext(), responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ResponseModel> converter = retrofit.responseBodyConverter(ResponseModel.class, new Annotation[0]);
                    ResponseModel errorModel = null;
                    try {
                        errorModel = converter.convert(response.errorBody());
                        Toast.makeText(getApplicationContext(), errorModel.getMessage(), Toast.LENGTH_SHORT).show();
                        if(401 == errorModel.getStatus()) {
                            SessionManager.clearUserSession(getApplicationContext());
                            startActivity(new Intent(getApplicationContext(), loginActivity.class));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<TechProject>>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Handle the back button press
        return true;
    }
}