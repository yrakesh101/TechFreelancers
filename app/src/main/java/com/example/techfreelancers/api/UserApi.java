package com.example.techfreelancers.api;

import com.example.techfreelancers.api.form.LoginForm;
import com.example.techfreelancers.api.form.RegisterForm;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    /**
     *
     * @param registerForm
     * @return
     */
    @POST("/mobile/user/register")
    Call<ResponseModel> userRegister(@Body RegisterForm registerForm);

    /**
     *
     * @param loginRequest
     * @return
     */
    @POST("/mobile/user/login")
    Call<ResponseModel<Map>> userLogin(@Body LoginForm loginRequest);
}
