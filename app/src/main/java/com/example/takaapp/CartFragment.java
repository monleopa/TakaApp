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
import android.widget.Button;
import android.widget.TextView;

import com.example.takaapp.Dto.OrderResponse;
import com.example.takaapp.Dto.UserRequestLogin;
import com.example.takaapp.Service.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartFragment extends Fragment implements AdapterCart.OnItemClickLisener {
    SharedPreferences sharedPreferences;
    private RecyclerView recycle_cart;
    private AdapterCart adapterCart;

    Button btnOrderBuy;
    TextView txtTotalCart, txtSizeCart;

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
        btnOrderBuy = view.findViewById(R.id.btnOrderBuy);
        txtSizeCart = view.findViewById(R.id.txtSizeCart);
        txtTotalCart = view.findViewById(R.id.txtTotalCart);

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
                adapterCart = new AdapterCart(CartFragment.this, cart.getItems(), getActivity().getSupportFragmentManager());
                recycle_cart.setAdapter(adapterCart);
                txtTotalCart.setText(String.valueOf(cart.getTotal()) + " đ");
                txtSizeCart.setText(String.valueOf(cart.getItems().size()) + " sản phẩm");
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {

            }
        });




        return view;
    }

    @Override
    public void onDeleteCart(int position) {
        adapterCart.removeItem(position);
    }
}
