package com.example.techfreelancers.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techfreelancers.adapter.categoryAdapter;
import com.example.techfreelancers.api.DictApi;
import com.example.techfreelancers.api.PaymentApi;
import com.example.techfreelancers.api.ResponseModel;
import com.example.techfreelancers.api.model.DictValue;
import com.example.techfreelancers.databinding.ActivityPaymentBinding;
import com.example.techfreelancers.utils.RetrofitClient;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityPaymentBinding paymentBinding;
    private PaymentSheet paymentSheet;
    private String secretKey;
    PaymentSheet.CustomerConfiguration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        paymentBinding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(paymentBinding.getRoot());

        init();
    }

    private void init() {
        fetchPaymentAPI();

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        paymentBinding.payButton.setOnClickListener(this);
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
        if(v.getId() == paymentBinding.payButton.getId()) {
            if(secretKey != null) {
                paymentSheet.presentWithPaymentIntent(secretKey, new PaymentSheet.Configuration("TechFreelancers", configuration));
            } else {
                Toast.makeText(this, "Payment API loading...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fetchPaymentAPI() {
        Retrofit retrofit = RetrofitClient.getInstance(this);
        Call<ResponseModel<Map>> call = retrofit.create(PaymentApi.class).initPaymentParrm();
        call.enqueue(new Callback<ResponseModel<Map>>() {
            @Override
            public void onResponse(Call<ResponseModel<Map>> call, Response<ResponseModel<Map>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getSuccess() && responseModel.getStatus() == 200) {
                        Map responseData = (Map) responseModel.getData();
                        configuration = new PaymentSheet.CustomerConfiguration(responseData.get("customer").toString(), responseData.get("ephemeralKey").toString());
                        secretKey = responseData.get("paymentIntent").toString();
                        PaymentConfiguration.init(getApplicationContext(), responseData.get("publishableKey").toString());
                    } else {
                        Toast.makeText(PaymentActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ResponseModel> converter = retrofit.responseBodyConverter(ResponseModel.class, new Annotation[0]);
                    ResponseModel errorModel = null;
                    try {
                        errorModel = converter.convert(response.errorBody());
                        Toast.makeText(PaymentActivity.this, errorModel.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<Map>> call, Throwable t) {
                Toast.makeText(PaymentActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}