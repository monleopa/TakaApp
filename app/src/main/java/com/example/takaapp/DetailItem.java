package com.example.takaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.takaapp.Dto.ItemResponse;

public class DetailItem extends AppCompatActivity {
    ImageView imgItemDetail;
    TextView txtItemDetailName, txtItemDetailPrice, txtBrand, txtSoLuong;
    Button btnDatMua, btnCongSoLuong, btnTruSoLuong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        init();

        ItemResponse itemResponse = (ItemResponse) getIntent().getExtras().get("item");

        Glide.with(this).load(itemResponse.getImg()).into(imgItemDetail);
        txtItemDetailName.setText(itemResponse.getName());
        txtBrand.setText(itemResponse.getBrand());
        txtItemDetailPrice.setText(String.valueOf(itemResponse.getPrice()) + " đ");
    }

    public void init(){
        imgItemDetail = findViewById(R.id.imgItemDetail);
        txtItemDetailName = findViewById(R.id.txtItemDetailName);
        txtItemDetailPrice = findViewById(R.id.txtItemDetailPrice);
        txtBrand = findViewById(R.id.txtBrand);
        btnDatMua = findViewById(R.id.btnDatMua);
        btnCongSoLuong = findViewById(R.id.btnCongSoLuong);
        btnTruSoLuong = findViewById(R.id.btnTruSoLuong);
        txtSoLuong = findViewById(R.id.txtSoLuong);
    }

    public void CongSoLuong(View view){
        int soluong = Integer.parseInt(txtSoLuong.getText().toString());
        soluong++;
        txtSoLuong.setText(String.valueOf(soluong));
    }

    public void TruSoLuong(View view){
        int soluong = Integer.parseInt(txtSoLuong.getText().toString());

        if(soluong != 1) {
            soluong--;
            txtSoLuong.setText(String.valueOf(soluong));
        }

    }
}
