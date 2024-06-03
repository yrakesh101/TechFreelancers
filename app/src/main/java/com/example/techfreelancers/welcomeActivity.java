package com.example.techfreelancers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.techfreelancers.api.UserLoginApi;
import com.example.techfreelancers.api.ResponseModel;
import com.example.techfreelancers.utils.RetrofitClient;
import com.example.techfreelancers.api.form.LoginForm;
import com.example.techfreelancers.utils.SessionManager;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class welcomeActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_acitivity); // Ensure the layout file name is correct
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = this.getSharedPreferences("login_user_info", Context.MODE_PRIVATE);

        Button signupBtn = findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(view -> {
            Intent intent = new Intent(welcomeActivity.this, signupActivity.class);
            startActivity(intent);
        });

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(welcomeActivity.this, loginActivity.class);
            startActivity(intent);

//            doUserLogin();
        });
    }

    private void doUserLogin() {
        LoginForm loginForm = new LoginForm("hello@gmail.com", "hellohello");
        Retrofit retrofit = RetrofitClient.getInstance(this);
        Call<ResponseModel> call = retrofit.create(UserLoginApi.class).userLogin(loginForm);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if(responseModel.getSuccess() && responseModel.getStatus() == 200) {
                        Map responseData = (LinkedTreeMap) responseModel.getData();
                        // save user information
                        SessionManager.saveUserSession(getApplicationContext(), responseData);
                        Toast.makeText(getApplicationContext(), "Login successful: " + responseData.get("email"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ResponseModel> converter = retrofit.responseBodyConverter(ResponseModel.class, new Annotation[0]);
                    ResponseModel errorModel = null;
                    try {
                        errorModel = converter.convert(response.errorBody());
                        Toast.makeText(getApplicationContext(), errorModel.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
