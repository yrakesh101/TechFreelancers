package com.example.techfreelancers.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techfreelancers.R;
import com.example.techfreelancers.api.PaymentApi;
import com.example.techfreelancers.api.ProjectApi;
import com.example.techfreelancers.api.ResponseModel;
import com.example.techfreelancers.api.model.TechProject;
import com.example.techfreelancers.databinding.ActivityPostGigBinding;
import com.example.techfreelancers.utils.RetrofitClient;
import com.example.techfreelancers.utils.SessionManager;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class postGigActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityPostGigBinding postGigBinding;
    private PaymentSheet paymentSheet;
    private String secretKey;
    PaymentSheet.CustomerConfiguration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postGigBinding = ActivityPostGigBinding.inflate(getLayoutInflater());
        setContentView(postGigBinding.getRoot());

        setSupportActionBar(postGigBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            Drawable navIcon = postGigBinding.toolbar.getNavigationIcon();
            if (navIcon != null) {
                @ColorInt int color = getResources().getColor(R.color.white);
                navIcon.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            }
        }

        init();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Handle the back button press
        return true;
    }

    private void init() {
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);
        postGigBinding.saveButton.setOnClickListener(this);
    }

    private void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if(paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(this, "Payment canceled.", Toast.LENGTH_SHORT).show();
        } else if(paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Toast.makeText(this, "Payment failed.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, ((PaymentSheetResult.Failed) paymentSheetResult).getError().getMessage(), Toast.LENGTH_SHORT).show();
        } else if(paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(this, "Payment success.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == postGigBinding.saveButton.getId()) {
            saveGigProject();
        }
    }

    private void saveGigProject() {
        String title = postGigBinding.titleEditText.getText().toString().trim();
        String price = postGigBinding.priceEditText.getText().toString().trim();
        String time = postGigBinding.timeEditText.getText().toString().trim();
        String description = postGigBinding.emailEditText.getText().toString().trim();
        TechProject project = new TechProject();
        if (!"".equals(title) && title.length() > 0) {
            project.setProjectTitle(title);
        } else {
            Toast.makeText(this, "Title cannot be null.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!"".equals(price) && price.length() > 0) {
            project.setProjectCost(BigDecimal.valueOf(Double.valueOf(price)));
        } else {
            Toast.makeText(this, "Price cannot be null.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!"".equals(time) && time.length() > 0) {
            project.setTimeSpan(time);
        } else {
            Toast.makeText(this, "Time cannot be null.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!"".equals(description) && description.length() > 0) {
            project.setProjectDetail(description);
        } else {
            Toast.makeText(this, "Description cannot be null.", Toast.LENGTH_SHORT).show();
            return;
        }
        Retrofit retrofit = RetrofitClient.getInstance(this);
        ProgressDialog progressDialog = new ProgressDialog(this, 1);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<ResponseModel<Integer>> call = retrofit.create(ProjectApi.class).saveTechProject(project);
        call.enqueue(new Callback<ResponseModel<Integer>>() {
            @Override
            public void onResponse(Call<ResponseModel<Integer>> call, Response<ResponseModel<Integer>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getSuccess() && responseModel.getStatus() == 200) {
                        Integer projectId = (Integer) responseModel.getData();
                        Toast.makeText(postGigActivity.this, "Gig project saved successfully.", Toast.LENGTH_SHORT).show();
                        fetchPaymentInfo(projectId);
                    } else {
                        Toast.makeText(postGigActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ResponseModel> converter = retrofit.responseBodyConverter(ResponseModel.class, new Annotation[0]);
                    ResponseModel errorModel = null;
                    try {
                        errorModel = converter.convert(response.errorBody());
                        Toast.makeText(postGigActivity.this, errorModel.getMessage(), Toast.LENGTH_SHORT).show();
                        if(401 == errorModel.getStatus()) {
                            SessionManager.clearUserSession(getApplicationContext());
                            startActivity(new Intent(getApplicationContext(), loginActivity.class));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<Integer>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(postGigActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void fetchPaymentInfo(Integer projectId) {
        ProgressDialog progressDialog = new ProgressDialog(postGigActivity.this, 1);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Retrofit retrofit = RetrofitClient.getInstance(this);
        // get project id
        Call<ResponseModel<Map>> call = retrofit.create(PaymentApi.class).initPaymentParrm(projectId);
        call.enqueue(new Callback<ResponseModel<Map>>() {
            @Override
            public void onResponse(Call<ResponseModel<Map>> call, Response<ResponseModel<Map>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getSuccess() && responseModel.getStatus() == 200) {
                        Map responseData = (Map) responseModel.getData();
                        configuration = new PaymentSheet.CustomerConfiguration(responseData.get("customer").toString(), responseData.get("ephemeralKey").toString());
                        secretKey = responseData.get("paymentIntent").toString();
                        PaymentConfiguration.init(getApplicationContext(), responseData.get("publishableKey").toString());
                        paymentSheet.presentWithPaymentIntent(secretKey, new PaymentSheet.Configuration("TechFreelancers", configuration));
                    } else {
                        Toast.makeText(postGigActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Converter<ResponseBody, ResponseModel> converter = retrofit.responseBodyConverter(ResponseModel.class, new Annotation[0]);
                    ResponseModel errorModel = null;
                    try {
                        errorModel = converter.convert(response.errorBody());
                        Toast.makeText(postGigActivity.this, errorModel.getMessage(), Toast.LENGTH_SHORT).show();
                        if(401 == errorModel.getStatus()) {
                            SessionManager.clearUserSession(getApplicationContext());
                            startActivity(new Intent(getApplicationContext(), loginActivity.class));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<Map>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(postGigActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}