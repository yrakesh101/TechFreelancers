package com.example.techfreelancers.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PaymentApi {

    /**
     * Query recommend projects
     * @return
     */
    @GET("/payment/project")
    Call<ResponseModel<Map>> initPaymentParrm(@Query("projectId") Integer projectId);
}
