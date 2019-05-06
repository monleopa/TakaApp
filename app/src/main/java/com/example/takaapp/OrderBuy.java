package com.example.takaapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.takaapp.Dto.OrderBuyRequest;
import com.example.takaapp.Dto.OrderResponse;
import com.example.takaapp.Dto.UserRequest;
import com.example.takaapp.Service.APIService;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderBuy extends AppCompatActivity {

    private static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(ConfigPaypal.PAYPAL_CLIENT_ID);

    EditText txtNameOrder, txtAddressOrder, txtPhoneOrder;
    Button btnOrder;
    RadioGroup radioGroup;
    RadioButton radioTypeBuy;
    String amount = "";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_buy);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        txtNameOrder = findViewById(R.id.txtNameOrder);
        txtAddressOrder = findViewById(R.id.txtAddressOrder);
        txtPhoneOrder = findViewById(R.id.txtPhoneOrder);
        btnOrder = findViewById(R.id.btnOrder);
        radioGroup = findViewById(R.id.radioGroup);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectID = radioGroup.getCheckedRadioButtonId();
                radioTypeBuy = findViewById(selectID);
                if(selectID == R.id.radio1) {
                    processOrder();
                }

                if(selectID == R.id.radio2){
                    processPayment();
                }
            }
        });
    }

    public void processOrder() {
        String name = txtNameOrder.getText().toString();
        String address = txtAddressOrder.getText().toString();
        String phone = txtPhoneOrder.getText().toString();

        String userId = sharedPreferences.getString("login", "");
        String loginType = sharedPreferences.getString("loginType", "");

        UserRequest user = new UserRequest(userId, loginType);

        OrderBuyRequest orderBuyRequest = new OrderBuyRequest(user, name, address, phone, "NotPay");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariable.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<OrderResponse> callBuy = apiService.executeOrder(orderBuyRequest);

        callBuy.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if(String.valueOf(response.code()) == "200"){
                    Intent intent = new Intent(OrderBuy.this, BuyResult.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {

            }
        });
    }

    public void processPayment() {
        amount = "500";
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD",
                "Success Payment", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        Log.d("Pay", "OK 1");
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PAYPAL_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null){
                    try {
                        String paymentDetail = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, BuyResult.class)
                                .putExtra("PaymentDetails", paymentDetail)
                                .putExtra("PaymentAmount", amount));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if (resultCode == Activity.RESULT_CANCELED){
                    Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        }
    }
}
