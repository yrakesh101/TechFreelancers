package com.example.techfreelancers.api;

import com.example.techfreelancers.api.form.LoginForm;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserLoginApi {
    @POST("/user/login")
    Call<ResponseModel> userLogin(@Body LoginForm loginRequest);
}
