package com.example.takaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BuyResult extends AppCompatActivity {

    TextView txtResult, txtAmount, txtStatus, txtCode;
    Button btnHome;
    ImageView imgResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_result);

        txtResult = findViewById(R.id.txtResult);
        txtAmount = findViewById(R.id.txtAmount);
        txtStatus = findViewById(R.id.txtStatus);
        txtCode = findViewById(R.id.txtCode);
        btnHome = findViewById(R.id.btnHome);
        imgResult = findViewById(R.id.imgResult);

        Intent intent = getIntent();

        String orderType = intent.getStringExtra("OrderType");
        String code = intent.getStringExtra("Code");
        long price = intent.getLongExtra("total", 0);
        Log.d("Order", "Order: "+orderType);
        Log.d("Order", "Order: "+price);
        if(orderType.equals("NotPay")) {
            imgResult.setImageResource(R.drawable.payment);
            txtResult.setText("Đặt hàng Thành công");
            txtAmount.setText("Số tiền phải trả: " +price+" đ");
            txtStatus.setText("Nhận hàng rồi thanh toán");
            txtCode.setText("Mã đơn hàng: "+code);
        }
        else {
            imgResult.setImageResource(R.drawable.payment);
            String paymentAmount = getIntent().getStringExtra("PaymentAmount");
            txtResult.setText("Đặt hàng thành công");
            txtAmount.setText("Số tiền đã trả: "+paymentAmount+" $");
            txtStatus.setText("Đã thanh toán");
            txtCode.setText("Mã đơn hàng: "+code);
        }

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BuyResult.this, MainActivity.class);
                startActivity(intent1);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(BuyResult.this,"Bấm nút quay lại trang chủ", Toast.LENGTH_SHORT).show();
    }
}
