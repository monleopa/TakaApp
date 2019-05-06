package com.example.takaapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.takaapp.Dto.ItemResponse;
import com.example.takaapp.Dto.OrderRequest;
import com.example.takaapp.Dto.OrderResponse;
import com.example.takaapp.Dto.UserRequest;
import com.example.takaapp.Dto.UserRequestLogin;
import com.example.takaapp.Dto.UserResponse;
import com.example.takaapp.Service.APIService;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailItemFrangment extends Fragment implements View.OnClickListener {

    ImageView imgItemDetail;
    TextView txtItemDetailName, txtItemDetailPrice, txtBrand, txtSoLuong, txtNumber;
    Button btnDatMua, btnCongSoLuong, btnTruSoLuong;
    SharedPreferences sharedPreferences;


    public static Fragment newInstance(ItemResponse itemResponse) {
        Fragment fragment = new DetailItemFrangment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", itemResponse);
        fragment.setArguments(bundle);
        return fragment;
    }

    public DetailItemFrangment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_detail_item_frangment, container, false);

        imgItemDetail = view.findViewById(R.id.imgItemDetail);
        txtItemDetailName = view.findViewById(R.id.txtItemDetailName);
        txtItemDetailPrice = view.findViewById(R.id.txtItemDetailPrice);
        txtBrand = view.findViewById(R.id.txtBrand);
        btnDatMua = view.findViewById(R.id.btnDatMua);
        btnCongSoLuong = view.findViewById(R.id.btnCongSoLuong);
        btnTruSoLuong = view.findViewById(R.id.btnTruSoLuong);
        txtSoLuong = view.findViewById(R.id.txtSoLuong);
        txtNumber = getActivity().findViewById(R.id.txtNumber);

        ItemResponse itemResponse = (ItemResponse) getArguments().getSerializable("item");

        Glide.with(this).load(itemResponse.getImg()).into(imgItemDetail);
        txtItemDetailName.setText(itemResponse.getName());
        txtBrand.setText(itemResponse.getBrand());
        txtItemDetailPrice.setText(String.valueOf(itemResponse.getPrice()) + " đ");

        btnCongSoLuong.setOnClickListener(this);
        btnTruSoLuong.setOnClickListener(this);
        btnDatMua.setOnClickListener(this);
        return view;
    }

    public void CongSoLuong() {
        int soluong = Integer.parseInt(txtSoLuong.getText().toString());
        soluong++;
        txtSoLuong.setText(String.valueOf(soluong));
    }

    public void TruSoLuong() {
        int soluong = Integer.parseInt(txtSoLuong.getText().toString());

        if (soluong != 1) {
            soluong--;
            txtSoLuong.setText(String.valueOf(soluong));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCongSoLuong:
                CongSoLuong();
                break;
            case R.id.btnTruSoLuong:
                TruSoLuong();
                break;
            case R.id.btnDatMua:
                addToCart();
                break;
        }
    }

    public void addToCart(){
        ItemResponse itemResponse = (ItemResponse) getArguments().getSerializable("item");
        int soluong = Integer.parseInt(txtSoLuong.getText().toString());

        sharedPreferences = getActivity().getSharedPreferences("loginPre", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("login", "");
        String type = sharedPreferences.getString("loginType", "");

        UserRequest user = new UserRequest(id, type);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariable.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        OrderRequest order = new OrderRequest(user, soluong, itemResponse.get_id());

        Call<OrderResponse> callOrder = apiService.getOrder(order);

        Log.d("response",txtSoLuong.getText().toString() );
        Log.d("response", itemResponse.get_id());

        callOrder.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                Log.d("response", String.valueOf(response.code()));
                if (String.valueOf(response.code()).equals("200")) {
                    Toast.makeText(getActivity(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                    OrderResponse order = response.body();
                    txtNumber.setText(order.getItems().size()+"");
                } else {
                    Toast.makeText(getActivity(), "Không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
