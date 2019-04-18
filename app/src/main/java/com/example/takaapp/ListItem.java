package com.example.takaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.takaapp.Dto.CategoryResponse;
import com.example.takaapp.Dto.ItemResponse;
import com.example.takaapp.Service.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListItem extends AppCompatActivity {

    private RecyclerView recycle_item;
    private TextView txtCategoryItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        recycle_item = findViewById(R.id.recycle_item);
        txtCategoryItem = findViewById(R.id.txtCategoryItem);

        String categoryID = getIntent().getExtras().getString("categoryID");
        String categoryName = getIntent().getExtras().getString("categoryName");

        txtCategoryItem.setText(categoryName);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariable.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<List<ItemResponse>> callItems = apiService.getAllItemsByCategory(categoryID);

        callItems.enqueue(new Callback<List<ItemResponse>>() {
            @Override
            public void onResponse(Call<List<ItemResponse>> call, Response<List<ItemResponse>> response) {
                List<ItemResponse> list = new ArrayList<>();
                list = response.body();
                AdapterItems ai = new AdapterItems(ListItem.this, list);
                recycle_item.setAdapter(ai);
            }

            @Override
            public void onFailure(Call<List<ItemResponse>> call, Throwable t) {

            }
        });


    }
}
