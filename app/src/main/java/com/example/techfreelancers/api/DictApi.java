package com.example.techfreelancers.api;

import com.example.techfreelancers.api.model.DictValue;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DictApi {

    /**
     * query dict value by dict key id
     * @param dictKeyId
     * @return
     */
    @GET("/dict/value/query")
    Call<ResponseModel<List<DictValue>>> queryDictValues(@Query("dictKeyId") Integer dictKeyId);
}
