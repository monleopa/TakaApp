package com.example.takaapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.takaapp.Dto.ItemResponse;
import com.example.takaapp.Dto.OrderResponse;
import com.example.takaapp.Dto.UserRequest;
import com.example.takaapp.Service.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterCart extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ItemResponse> list;
    private FragmentManager fragmentManager;
    private OnItemClickLisener mListener;
    SharedPreferences sharedPreferences;


    public interface OnItemClickLisener {
        void onDeleteCart(int position);
    }

    public AdapterCart(OnItemClickLisener lisener, List<ItemResponse> list, FragmentManager fragmentManager) {
        mListener = lisener;
        this.list = list;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_detail, viewGroup, false);
        return new AdapterCart.ViewHolderCart(view, viewGroup.getContext(), mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((AdapterCart.ViewHolderCart) viewHolder).bindData(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolderCart extends RecyclerView.ViewHolder {
        private Context context;
        ImageView imgItemCart;
        TextView txtNameCart, txtPriceCart, txtSoluongCart;
        ConstraintLayout cart_layout;
        ImageView btnDeleteCart;

        public ViewHolderCart(@NonNull View cartView, Context context, final OnItemClickLisener listener) {
            super(cartView);
            imgItemCart = cartView.findViewById(R.id.imgItemCart);
            txtNameCart = cartView.findViewById(R.id.txtNameCart);
            txtPriceCart = cartView.findViewById(R.id.txtPriceCart);
            txtSoluongCart = cartView.findViewById(R.id.txtSoluongCart);
            cart_layout = cartView.findViewById(R.id.cart_layout);
            btnDeleteCart = cartView.findViewById(R.id.btnDeleteCart);
            this.context = context;

            btnDeleteCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteCart(position);
                        }
                    }
                }
            });
        }

        public void bindData(ItemResponse itemResponse) {
            txtNameCart.setText(itemResponse.getName());
            txtPriceCart.setText(String.valueOf(itemResponse.getPrice()) + " đ");
            txtSoluongCart.setText(String.valueOf(itemResponse.getNumber()));
            Glide.with(context).load(itemResponse.getImg()).into(imgItemCart);
        }

    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public String takeIdItem(int position) {
        ItemResponse item = list.get(position);

        return item.get_id();
    }
}
