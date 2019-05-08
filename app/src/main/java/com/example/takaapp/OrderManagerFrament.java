package com.example.takaapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.takaapp.Dto.OrderResponse;
import com.example.takaapp.Dto.UserRequest;
import com.example.takaapp.Service.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OrderManagerFrament extends Fragment implements AdapterOrder.OnItemClick{

    SharedPreferences sharedPreferences;
    private RecyclerView recycle_order;
    private ProgressBar progressBarOrder;

    public static Fragment newInstance() {
        Fragment fragment = new OrderManagerFrament();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_order_manager_frament, container, false);

        progressBarOrder = view.findViewById(R.id.progressBarOrder);
        recycle_order = view.findViewById(R.id.recycle_order);
        sharedPreferences = getActivity().getSharedPreferences("loginPre", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("login", "");
        String type = sharedPreferences.getString("loginType", "");

        UserRequest user = new UserRequest(id, type);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVariable.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<List<OrderResponse>> callOrder = apiService.getAllOrders(user);

        callOrder.enqueue(new Callback<List<OrderResponse>>() {
            @Override
            public void onResponse(Call<List<OrderResponse>> call, Response<List<OrderResponse>> response) {
                if(response.code() == 200) {
                    List<OrderResponse> list = response.body();

                    Log.d("test","test: "+list.size());
                    for(int i=0;i<list.size();i++) {
                        if(list.get(i).getStatus().equals("CART")){
                            list.remove(i);
                        }
                    }

                    Log.d("test","test: "+list.size());
                    progressBarOrder.setVisibility(View.GONE);
                    AdapterOrder ao = new AdapterOrder(list, OrderManagerFrament.this);
                    recycle_order.setAdapter(ao);

                }
            }

            @Override
            public void onFailure(Call<List<OrderResponse>> call, Throwable t) {

            }
        });

        return view;
    }

    @Override
    public void onOrderClick(OrderResponse orderResponse) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, CartFragment.newInstance2(orderResponse.get_id()));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
