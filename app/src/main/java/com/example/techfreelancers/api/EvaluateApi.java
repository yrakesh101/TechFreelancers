package com.example.techfreelancers.api;

import com.example.techfreelancers.api.form.EvaluateForm;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EvaluateApi {

    /**
     *
     * @param evaluation
     * @return
     */
    @POST("/evaluate/accepter")
    Call<ResponseModel> evaluateAccepter(@Body EvaluateForm evaluation);

    /**
     *
     * @param evaluation
     * @return
     */
    @POST("/evaluate/publisher")
    Call<ResponseModel> evaluatePublisher(@Body EvaluateForm evaluation);
}
