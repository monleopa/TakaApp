package com.example.takaapp;

import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.example.takaapp.Dto.ItemResponse;

public class DetailItemFrangment extends Fragment implements View.OnClickListener {

    ImageView imgItemDetail;
    TextView txtItemDetailName, txtItemDetailPrice, txtBrand, txtSoLuong;
    Button btnDatMua, btnCongSoLuong, btnTruSoLuong;


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

        ItemResponse itemResponse = (ItemResponse) getArguments().getSerializable("item");
        Log.d("ducanh123", "onCreateView: ");
        Glide.with(this).load(itemResponse.getImg()).into(imgItemDetail);
        txtItemDetailName.setText(itemResponse.getName());
        txtBrand.setText(itemResponse.getBrand());
        txtItemDetailPrice.setText(String.valueOf(itemResponse.getPrice()) + " đ");

        btnCongSoLuong.setOnClickListener(this);
        btnTruSoLuong.setOnClickListener(this);
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
        }
    }
}
