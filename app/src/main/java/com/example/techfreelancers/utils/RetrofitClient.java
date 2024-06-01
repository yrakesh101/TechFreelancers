package com.example.techfreelancers.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String DEFAULT_BASE_API_URL = "http://10.10.10.181:8090";
    private static final Integer DEFAULT_REQUEST_TIMEOUT = 10000;
    private static final String USER_SESSION_KEY = "LOGIN_USER_INFO";
    private static Retrofit retrofit;

    public static Retrofit getInstance(Context context) {
        // load base api url and request timeout from config file
        Properties config = ConfigUtils.loadConfig(context);
        String baseApiUrl;
        int requestTimeout;
        if (config != null) {
            baseApiUrl = config.getProperty("api_base_url");
            requestTimeout = Integer.parseInt(config.getProperty("timeout"));
        } else {
            baseApiUrl = DEFAULT_BASE_API_URL;
            requestTimeout = DEFAULT_REQUEST_TIMEOUT;
        }
        if (retrofit == null) {
            // Configure OkHttpClient with custom settings
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SESSION_KEY, Context.MODE_PRIVATE);
                    String userToken = sharedPreferences.getString("USER_TOKEN", null);
                    if (userToken != null) {
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization", "Bearer " + userToken);
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    } else {
                        return chain.proceed(original);
                    }
                }
            });
            OkHttpClient client = httpClient
                    .connectTimeout(requestTimeout, TimeUnit.MILLISECONDS) // Connection timeout
                    .readTimeout(requestTimeout, TimeUnit.MILLISECONDS)    // Read timeout
                    .writeTimeout(requestTimeout, TimeUnit.MILLISECONDS)   // Write timeout
                    .build();
            // Create Retrofit instance with custom OkHttpClient
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseApiUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
