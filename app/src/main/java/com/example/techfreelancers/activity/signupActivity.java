package com.example.techfreelancers.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techfreelancers.api.ResponseModel;
import com.example.techfreelancers.api.UserApi;
import com.example.techfreelancers.api.form.RegisterForm;
import com.example.techfreelancers.databinding.ActivitySignUpBinding;
import com.example.techfreelancers.utils.HashUtil;
import com.example.techfreelancers.utils.RetrofitClient;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class signupActivity extends AppCompatActivity {
    ActivitySignUpBinding signUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());

        setVariable();

    }


    private void setVariable() {
        signUpBinding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signUpBinding.emailEditText.getText().toString();
                String password = signUpBinding.passwordEditText.getText().toString();
                String confpassword = signUpBinding.confpasswordEditText.getText().toString();
                if (!"".equals(email) && email.length() > 0) {

                } else {
                    Toast.makeText(signupActivity.this, "Email cannot be null.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!"".equals(password) && password.length() > 0) {

                } else {
                    Toast.makeText(signupActivity.this, "Password cannot be null.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!"".equals(confpassword) && confpassword.length() > 0) {

                } else {
                    Toast.makeText(signupActivity.this, "Confirm password cannot be null.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(signupActivity.this, "Password is too short.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confpassword)) {
                    Toast.makeText(signupActivity.this, "Password doesn't equal to confirm password.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String hashPassword = HashUtil.hashPassword(password, signupActivity.this);
                Retrofit retrofit = RetrofitClient.getInstance(signupActivity.this);
                ProgressDialog progressDialog = new ProgressDialog(signupActivity.this, 1);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Call<ResponseModel> call = retrofit.create(UserApi.class).userRegister(new RegisterForm(email, hashPassword));
                call.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful() && response.body() != null) {
                            ResponseModel responseModel = response.body();
                            if (responseModel.getSuccess() && responseModel.getStatus() == 200) {
                                Toast.makeText(signupActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(signupActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Converter<ResponseBody, ResponseModel> converter = retrofit.responseBodyConverter(ResponseModel.class, new Annotation[0]);
                            ResponseModel errorModel = null;
                            try {
                                errorModel = converter.convert(response.errorBody());
                                Toast.makeText(signupActivity.this, errorModel.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(signupActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });
        signUpBinding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signupActivity.this, loginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}