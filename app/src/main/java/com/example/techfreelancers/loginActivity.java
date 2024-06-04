package com.example.techfreelancers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.techfreelancers.api.ResponseModel;
import com.example.techfreelancers.api.UserApi;
import com.example.techfreelancers.api.form.LoginForm;
import com.example.techfreelancers.databinding.ActivityLoginBinding;
import com.example.techfreelancers.utils.HashUtil;
import com.example.techfreelancers.utils.RetrofitClient;
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

public class loginActivity extends AppCompatActivity {

    ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_login);

        EditText emailEditText = findViewById(R.id.emailEditText);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginBinding.emailEditText.setText("hello@gmail.com");
        loginBinding.passwordEditText.setText("hellohello");

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> doUserLogin());

        Button signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(view -> {
            Intent intent = new Intent(loginActivity.this, signupActivity.class);
            startActivity(intent);
        });
    }

    private void doUserLogin() {
        String email = loginBinding.emailEditText.getText().toString().trim();
        String password = loginBinding.passwordEditText.getText().toString().trim();
        if(!"".equals(email) && email.length() > 0) {

        } else {
            Toast.makeText(loginActivity.this, "Email cannot be null.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!"".equals(password) && password.length() > 0) {

        } else {
            Toast.makeText(loginActivity.this, "Password cannot be null.", Toast.LENGTH_SHORT).show();
            return;
        }
        String hashPassword = HashUtil.hashPassword(password, getApplicationContext());
        Retrofit retrofit = RetrofitClient.getInstance(this);
        Call<ResponseModel> call = retrofit.create(UserApi.class).userLogin(new LoginForm(email, hashPassword));
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if(responseModel.getSuccess() && responseModel.getStatus() == 200) {
                        Map responseData = (LinkedTreeMap) responseModel.getData();
                        // save user information
                        SessionManager.saveUserSession(loginActivity.this, responseData);
                        Toast.makeText(loginActivity.this, "Login successful: " + responseData.get("email"), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(loginActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
