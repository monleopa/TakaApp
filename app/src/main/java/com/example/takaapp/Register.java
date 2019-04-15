package com.example.takaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.takaapp.Dto.UserRequestRegister;
import com.example.takaapp.Dto.UserResponse;
import com.example.takaapp.Service.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText edtResTaikhoan, edtResMatkhau, edtResTen, edtResEmail, edtResSodienthoai;
    Button btnResDangky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        btnResDangky.setOnClickListener(this);
    }

    public void init(){
        edtResTaikhoan = findViewById(R.id.edtResTaikhoan);
        edtResMatkhau = findViewById(R.id.edtResMatkhau);
        edtResTen = findViewById(R.id.edtResTen);
        edtResEmail = findViewById(R.id.edtResEmail);
        edtResSodienthoai = findViewById(R.id.edtResSodienthoai);
        btnResDangky = findViewById(R.id.btnResDangky);
    }

    @Override
    public void onClick(View v) {
        String username = String.valueOf(edtResTaikhoan.getText());
        String password = String.valueOf(edtResMatkhau.getText());
        String name = String.valueOf(edtResTen.getText());
        String email = String.valueOf(edtResEmail.getText());
        String phone = String.valueOf(edtResSodienthoai.getText());

        UserRequestRegister user = new UserRequestRegister(username, password, name, email, phone);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariable.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<UserResponse> callUser = apiService.register(user);

        callUser.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(String.valueOf(response.code()).equals("200")){
                    Toast.makeText(Register.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Register.this, "Không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
