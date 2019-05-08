package com.example.takaapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

    private ProgressBar progressBarListItem;

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
        progressBarListItem = view.findViewById(R.id.progressBarListItem);

        Log.d("Test", "Vao dc nhe");

        final String categoryID = getArguments().getString("categoryID");
        final String categoryName = getArguments().getString("categoryName");

        txtCategoryItem.setText("loading ...");

        if(categoryID.equals("search")) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GlobalVariable.url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService apiService = retrofit.create(APIService.class);
            Log.d("search", categoryName);
            Call<List<ItemResponse>> callItems = apiService.getAllItemsByName(categoryName);

            callItems.enqueue(new Callback<List<ItemResponse>>() {
                @Override
                public void onResponse(Call<List<ItemResponse>> call, Response<List<ItemResponse>> response) {
                    progressBarListItem.setVisibility(View.GONE);
                    if (String.valueOf(response.code()).equals("200")) {
                        List<ItemResponse> list = response.body();
                        if(list.size() > 0){
                            txtCategoryItem.setText(categoryName);
                            AdapterItems ai = new AdapterItems(getActivity().getSupportFragmentManager(), list);
                            recycle_item.setAdapter(ai);
                        } else {
                            txtCategoryItem.setText("Chưa có sản phẩm nào có tên: "+categoryName);
                        }

                    } else {
                        Toast.makeText(getActivity(), "Hiện chưa có sản phẩm này", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<ItemResponse>> call, Throwable t) {

                }
            });
        }
        else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GlobalVariable.url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService apiService = retrofit.create(APIService.class);

            Call<List<ItemResponse>> callItems = apiService.getAllItemsByCategory(categoryID);

            callItems.enqueue(new Callback<List<ItemResponse>>() {
                @Override
                public void onResponse(Call<List<ItemResponse>> call, Response<List<ItemResponse>> response) {
                    progressBarListItem.setVisibility(View.GONE);
                    if (String.valueOf(response.code()).equals("200")) {
                        List<ItemResponse> list = response.body();
                        if (list.size() > 0) {
                            txtCategoryItem.setText(categoryName);
                            AdapterItems ai = new AdapterItems(getActivity().getSupportFragmentManager(), list);
                            recycle_item.setAdapter(ai);
                        }
                        else {
                            txtCategoryItem.setText("Chưa có sản phẩm nào có tên: "+categoryName);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ItemResponse>> call, Throwable t) {

                }
            });
        }

        return view;

    }
}
