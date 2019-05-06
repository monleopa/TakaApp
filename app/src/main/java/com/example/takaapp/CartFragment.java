package com.example.takaapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.takaapp.Dto.ItemResponse;
import com.example.takaapp.Dto.OrderResponse;
import com.example.takaapp.Dto.UserRequest;
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
        String login = sharedPreferences.getString("login", "");
        String type = sharedPreferences.getString("loginType", "");

        UserRequest user = new UserRequest(login, type);
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


        btnOrderBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderBuy.class);
                startActivity(intent);
            }
        });




        return view;
    }

    @Override
    public void onDeleteCart(int position) {
        deleteAlert("Xóa sản phẩm", "Bạn có chắc muốn xóa sản phẩm này khỏi giỏ hàng?", position);
    }

    public void deleteAlert(String title, String message, final int position) {
        final AlertDialog.Builder builderSinger = new AlertDialog.Builder(getActivity());
        builderSinger.setIcon(R.drawable.alert);
        builderSinger.setTitle(title);
        builderSinger.setMessage(message);

        sharedPreferences = getActivity().getSharedPreferences("loginPre", Context.MODE_PRIVATE);
        final String login = sharedPreferences.getString("login", "");
        final String type = sharedPreferences.getString("loginType", "");

        builderSinger.setPositiveButton(
                "Đồng ý",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        String id = adapterCart.takeIdItem(position);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(GlobalVariable.url)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        APIService apiService = retrofit.create(APIService.class);

                        UserRequest userRequest = new UserRequest(login, type);

                        Log.d("delete", "delete: " + login);
                        Log.d("delete", "delete: " + type);
                        Log.d("delete", "delete: " + id);


                        Call<OrderResponse> deleteItem = apiService.deleteFromCart(id, userRequest);

                        deleteItem.enqueue(new Callback<OrderResponse>() {
                            @Override
                            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                                Log.d("delete", "delete: " + response.code());
                                if(response.code() == 200) {
                                    int size = (response.body().getItems().size());
                                    txtSizeCart.setText(String.valueOf(size));
                                    TextView txtNumber = getActivity().findViewById(R.id.txtNumber);
                                    txtNumber.setText(String.valueOf(size));
                                    txtTotalCart.setText(String.valueOf(response.body().getTotal()));
                                    adapterCart.removeItem(position);
                                    Toast.makeText(getActivity(),"Thành công !!!", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getActivity(),"Lỗi !!!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<OrderResponse> call, Throwable t) {

                            }
                        });
                    }
                }
        );

        builderSinger.setNegativeButton(
                "Hủy bỏ",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                }
        );

        builderSinger.show();
    }
}
