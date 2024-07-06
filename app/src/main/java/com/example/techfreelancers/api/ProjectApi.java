package com.example.techfreelancers.api;

import com.example.techfreelancers.api.model.TechProject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProjectApi {

    /**
     * Query recommend projects
     * @return
     */
    @GET("/tech/project/recommend")
    Call<ResponseModel<List<TechProject>>> queryRecommendProject();
}
