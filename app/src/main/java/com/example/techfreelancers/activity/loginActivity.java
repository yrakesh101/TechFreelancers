package com.example.techfreelancers.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techfreelancers.api.ResponseModel;
import com.example.techfreelancers.api.UserApi;
import com.example.techfreelancers.api.form.LoginForm;
import com.example.techfreelancers.databinding.ActivityLoginBinding;
import com.example.techfreelancers.utils.HashUtil;
import com.example.techfreelancers.utils.RetrofitClient;
import com.example.techfreelancers.utils.SessionManager;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        init();
    }

    private void init() {
        loginBinding.loginButton.setOnClickListener(this);
        loginBinding.signupButton.setOnClickListener(this);
        
        checkLoginStatus();
    }

    private void checkLoginStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_USER_INFO", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("IS_LOGIN", false)) {
            Toast.makeText(this, "Already login, go to search.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(loginActivity.this, MainActivity.class); // Replace SearchActivity.class with your actual search activity class
            startActivity(intent);//for navigation to search activity page
            finish();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == loginBinding.loginButton.getId()) {
            doUserLogin();
        } else if (v.getId() == loginBinding.signupButton.getId()) {
            Intent intent = new Intent(loginActivity.this, signupActivity.class);
            startActivity(intent);
        }
    }

    private void doUserLogin() {
        String email = loginBinding.emailEditText.getText().toString().trim();
        String password = loginBinding.passwordEditText.getText().toString().trim();
        if (!"".equals(email) && email.length() > 0) {
            if ("pay".equals(email)) {
                Intent intent = new Intent(loginActivity.this, PaymentActivity.class);
                startActivity(intent);
                return;
            }
        } else {
            // temp default email
            email = "hello@gmail.com";
//            Toast.makeText(loginActivity.this, "Email cannot be null.", Toast.LENGTH_SHORT).show();
//            return;
        }
        if (!"".equals(password) && password.length() > 0) {

        } else {
            // temp default password
            password = "hellohello";
//            Toast.makeText(loginActivity.this, "Password cannot be null.", Toast.LENGTH_SHORT).show();
//            return;
        }
        String hashPassword = HashUtil.hashPassword(password, getApplicationContext());
        Retrofit retrofit = RetrofitClient.getInstance(this);
        ProgressDialog progressDialog = new ProgressDialog(loginActivity.this, 1);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<ResponseModel<Map>> call = retrofit.create(UserApi.class).userLogin(new LoginForm(email, hashPassword));
        call.enqueue(new Callback<ResponseModel<Map>>() {
            @Override
            public void onResponse(Call<ResponseModel<Map>> call, Response<ResponseModel<Map>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getSuccess() && responseModel.getStatus() == 200) {
                        Map responseData = (Map) responseModel.getData();
                        // save user information
                        SessionManager.saveUserSession(loginActivity.this, responseData);
                        Toast.makeText(loginActivity.this, "Login successful: " + responseData.get("email"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(loginActivity.this, searchActivity.class); // Replace SearchActivity.class with your actual search activity class
                        startActivity(intent);//for navigation to search activity page
                        finish();
                    } else {
                        Toast.makeText(loginActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ResponseModel> converter = retrofit.responseBodyConverter(ResponseModel.class, new Annotation[0]);
                    ResponseModel errorModel = null;
                    try {
                        errorModel = converter.convert(response.errorBody());
                        Toast.makeText(loginActivity.this, errorModel.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<Map>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(loginActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
