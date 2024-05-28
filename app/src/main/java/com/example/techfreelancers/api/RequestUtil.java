package com.example.techfreelancers.api;

import android.widget.Toast;

import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class RequestUtil {
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();

    public interface NetworkCallback<ResponseModel> {
        void onSuccess(ResponseModel result);
        void onFailure(Exception e);
    }

    public static <T> void makeGetRequest(String url, Class<T> responseClass, NetworkCallback<T> callback) {
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    String responseData = response.body().string();
                    T result = gson.fromJson(responseData, responseClass);
                    callback.onSuccess(result);
                } else {
                    callback.onFailure(new IOException("Response body is null"));
                }
            }
        });
    }

    public static <T> void makePostRequest(String url, Object requestBodyObject, Class<T> responseClass, NetworkCallback<T> callback) {
        String json = gson.toJson(requestBodyObject);
        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder().url(url).post(requestBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    String responseData = response.body().string();
                    T result = gson.fromJson(responseData, responseClass);
                    callback.onSuccess(result);
                } else {
                    callback.onFailure(new IOException("Response body is null"));
                }
            }
        });
    }
}
