package com.example.takaapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.takaapp.Dto.CategoryResponse;
import com.example.takaapp.Dto.OrderResponse;
import com.example.takaapp.Dto.UserRequestLogin;
import com.example.takaapp.Dto.UserRequestRegister;
import com.example.takaapp.Dto.UserResponse;
import com.example.takaapp.Service.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, TextView.OnEditorActionListener, AdapterCategory.OnItemClick {

    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private ImageView imgMenu;
    private EditText edtSearch;
    private RecyclerView recycle_category;
    private ImageView imgAvatar;
    private TextView header_username, header_email, txtNumber;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editorShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("loginPre", MODE_PRIVATE);

        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");

        imgMenu = findViewById(R.id.imgMenu);

        imgMenu.setOnClickListener(this);

        dl = findViewById(R.id.dl);
        txtNumber = findViewById(R.id.txtNumber);

        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        recycle_category = findViewById(R.id.recycle_category);
        imgMenu = findViewById(R.id.imgMenu);
        edtSearch = findViewById(R.id.edtSearch);
        edtSearch.setOnEditorActionListener(this);

        NavigationView nav_view = findViewById(R.id.nav_view);
        header_email = nav_view.getHeaderView(0).findViewById(R.id.header_email);
        header_username = nav_view.getHeaderView(0).findViewById(R.id.header_username);
        imgAvatar = nav_view.getHeaderView(0).findViewById(R.id.imgAvatar);


        if (sharedPreferences.getString("login", "0").equals("0")) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }
        else
        {
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
                        header_username.setText(userResponse.getName());
                        header_email.setText(userResponse.getEmail());
                        Glide.with(MainActivity.this).load(userResponse.getAvatar()).into(imgAvatar);
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            Call<OrderResponse> callCart = apiService.getCart(user);
            callCart.enqueue(new Callback<OrderResponse>() {
                @Override
                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                    if (String.valueOf(response.code()).equals("200")) {
                        OrderResponse cart = response.body();
                        txtNumber.setText(cart.getItems().size() + "");
                    }
                }

                @Override
                public void onFailure(Call<OrderResponse> call, Throwable t) {

                }
            });
        }

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.profile) {
                    Toast.makeText(MainActivity.this, "My Profile", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.danhmuc) {
                    Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.editprofile) {
                    Toast.makeText(MainActivity.this, "Edit Profile", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.logout) {
                    editorShared = sharedPreferences.edit();
                    editorShared.putString("login", "0");
                    editorShared.putString("username", "");
                    editorShared.putString("password", "");
                    editorShared.commit();

                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
//                    finish();
                } else if (id == R.id.trangchu) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariable.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<List<CategoryResponse>> callCategory = apiService.getAllCategory();

        callCategory.enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {
                List<CategoryResponse> list = new ArrayList<>();
                list = response.body();
                AdapterCategory ac = new AdapterCategory(MainActivity.this, list);
                recycle_category.setAdapter(ac);
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgMenu:
                dl.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Toast.makeText(MainActivity.this, edtSearch.getText().toString(), Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(String id, String name) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, ListItemFragment.newInstance(id, name));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public void viewCart(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, CartFragment.newInstance());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
