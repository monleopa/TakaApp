package com.example.takaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class BuyResult extends AppCompatActivity {

    TextView txtResult, txtAmount, txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_result);

        txtResult = findViewById(R.id.txtResult);
        txtAmount = findViewById(R.id.txtAmount);
        txtStatus = findViewById(R.id.txtStatus);

        Intent intent = getIntent();

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void showDetails(JSONObject response, String paymentAmount) {
        try {
            txtResult.setText(response.getString("id"));
            txtAmount.setText(response.getString("state"));
            txtStatus.setText(response.getString(String.format("%s đ",paymentAmount)));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
