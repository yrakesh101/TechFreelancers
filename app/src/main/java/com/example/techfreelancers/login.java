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

import com.example.techfreelancers.api.RequestUtil;
import com.example.techfreelancers.api.ResponseModel;
import com.example.techfreelancers.api.form.LoginForm;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        EditText emailEditText = findViewById(R.id.emailEditText);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> {
            doUserLogin();
        });

        Button signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(view -> {
            // Start signUpPage activity
            Intent intent = new Intent(login.this, signUpPage.class);
            startActivity(intent);
        });
    }

    private void doUserLogin() {
        String url = "http://10.10.10.181:8090/hello/world/post";
        LoginForm loginForm = new LoginForm("helloworld", "passwordpassword");
        RequestUtil.makePostRequest(url, loginForm, ResponseModel.class, new RequestUtil.NetworkCallback<ResponseModel>() {
            @Override
            public void onSuccess(ResponseModel result) {
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "User login success.", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}
