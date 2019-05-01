package com.example.takaapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.takaapp.Dto.UserRequestLogin;
import com.example.takaapp.Dto.UserResponse;
import com.example.takaapp.Service.APIService;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText edtTaikhoan, edtMatkhau;
    Button btnDangnhap, btnDangky;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editorShared;

    private CallbackManager mCallbackManager;
    private static final String TAG = "FACELOG";

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

        Log.d(TAG, "onCreate: " + getKey());
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.buttonFacebookLogin);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                //handleFacebookAccessToken(loginResult.getAccessToken());
                sharedPreferences = getSharedPreferences("loginPre", MODE_PRIVATE);
                editorShared = sharedPreferences.edit();
                editorShared.putString("login", loginResult.getAccessToken().getUserId());
                editorShared.commit();

                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError" + error.getMessage());
                // ...
            }
        });

    }

    private String getKey(){

            PackageInfo packageInfo;
            String key = null;
            try {
                //getting application package name, as defined in manifest
                String packageName = this.getApplicationContext().getPackageName();

                //Retriving package info
                packageInfo = getPackageManager().getPackageInfo(packageName,
                        PackageManager.GET_SIGNATURES);

                Log.e("Package Name=", getApplicationContext().getPackageName());

                for (android.content.pm.Signature signature : packageInfo.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    key = new String(Base64.encode(md.digest(), 0));

                    // String key = new String(Base64.encodeBytes(md.digest()));
                    Log.e("Key Hash=", key);
                }
            } catch (PackageManager.NameNotFoundException e1) {
                Log.e("Name not found", e1.toString());
            } catch (NoSuchAlgorithmException e) {
                Log.e("No such an algorithm", e.toString());
            } catch (Exception e) {
                Log.e("Exception", e.toString());
            }

            return key;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void init() {
        edtTaikhoan = findViewById(R.id.edtResTaikhoan);
        edtMatkhau = findViewById(R.id.edtMatkhau);
        btnDangnhap = findViewById(R.id.btnDangnhap);
        btnDangky = findViewById(R.id.btnDangky);
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
                    editorShared.putString("username", userResponse.getUsername());
                    editorShared.putString("password", userResponse.getPassword());
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
