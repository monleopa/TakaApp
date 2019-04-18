package com.example.takaapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.takaapp.Dto.ItemResponse;
import com.example.takaapp.Service.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListItemFragment extends Fragment {

    private RecyclerView recycle_item;
    private TextView txtCategoryItem;

    public static Fragment newInstance(String categoryID, String categoryName) {
        Fragment fragment = new ListItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("categoryID", categoryID);
        bundle.putString("categoryName", categoryName);
        fragment.setArguments(bundle);
        return fragment;
    }

    public ListItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list_item, container, false);
        recycle_item = view.findViewById(R.id.recycle_item);
        txtCategoryItem = view.findViewById(R.id.txtCategoryItem);

        String categoryID = getArguments().getString("categoryID");
        String categoryName = getArguments().getString("categoryName");

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
                List<ItemResponse> list;
                list = response.body();
                AdapterItems ai = new AdapterItems(getActivity().getSupportFragmentManager(), list);
                recycle_item.setAdapter(ai);
            }

            @Override
            public void onFailure(Call<List<ItemResponse>> call, Throwable t) {

            }
        });
        return view;

    }
}
