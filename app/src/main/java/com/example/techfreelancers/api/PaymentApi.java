package com.example.techfreelancers.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PaymentApi {

    /**
     * Query recommend projects
     * @return
     */
    @GET("/payment/init")
    Call<ResponseModel<Map>> initPaymentParrm();
}
