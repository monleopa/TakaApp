package com.example.takaapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.takaapp.Dto.OrderResponse;
import com.example.takaapp.Dto.UserRequestLogin;
import com.example.takaapp.Service.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartFragment extends Fragment {
    SharedPreferences sharedPreferences;
    private RecyclerView recycle_cart;

    public static Fragment newInstance() {
        Fragment fragment = new CartFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    public CartFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_cart, container, false);

        recycle_cart = view.findViewById(R.id.recycle_cart);
        Log.d("TEST", "vao dc day roi ne ");

        sharedPreferences = getActivity().getSharedPreferences("loginPre", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");

        UserRequestLogin user = new UserRequestLogin(username, password);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariable.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<OrderResponse> callCart = apiService.getCart(user);

        callCart.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                OrderResponse cart = response.body();
                AdapterCart ac = new AdapterCart(cart.getItems(), getActivity().getSupportFragmentManager());
                recycle_cart.setAdapter(ac);
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {

            }
        });




        return view;
    }

}
