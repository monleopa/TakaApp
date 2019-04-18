package com.example.takaapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.takaapp.Dto.UserRequestLogin;
import com.example.takaapp.Dto.UserResponse;
import com.example.takaapp.Service.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText edtTaikhoan, edtMatkhau;
    Button btnDangnhap, btnDangky, btnFacebook;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editorShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        btnDangnhap.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("loginPre", MODE_PRIVATE);
        editorShared = sharedPreferences.edit();
        if (!sharedPreferences.getString("login", "0").equals("0")) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public void init() {
        edtTaikhoan = findViewById(R.id.edtResTaikhoan);
        edtMatkhau = findViewById(R.id.edtMatkhau);
        btnDangnhap = findViewById(R.id.btnDangnhap);
        btnDangky = findViewById(R.id.btnDangky);
        btnFacebook = findViewById(R.id.btnFacebook);
    }

    @Override
    public void onClick(View v) {
        String username = String.valueOf(edtTaikhoan.getText());
        String password = String.valueOf(edtMatkhau.getText());

        UserRequestLogin user = new UserRequestLogin(username, password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariable.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<UserResponse> callUser = apiService.login(user);

        callUser.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if (String.valueOf(response.code()).equals("200")) {
                    UserResponse userResponse = response.body();
                    sharedPreferences = getSharedPreferences("loginPre", MODE_PRIVATE);
                    editorShared = sharedPreferences.edit();
                    editorShared.putString("login", userResponse.get_id());
                    editorShared.commit();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("user", userResponse);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, "Sai mật khẩu hoặc tài khoản", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public void onDangky(View view) {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }
}
