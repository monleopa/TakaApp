package com.example.takaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.takaapp.Dto.ItemResponse;

import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ItemResponse> list;
    private FragmentManager fragmentManager;
    private AdapterCategory.OnItemClick onItemClick;

    public AdapterCart(List<ItemResponse> list, FragmentManager fragmentManager) {
        this.list = list;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_detail, viewGroup, false);
        return new AdapterCart.ViewHolderCart(view, viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((AdapterCart.ViewHolderCart) viewHolder).bindData(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolderCart extends RecyclerView.ViewHolder{
        private Context context;
        ImageView imgItemCart;
        TextView txtNameCart, txtPriceCart, txtSoluongCart;
        ConstraintLayout cart_layout;

        public ViewHolderCart(@NonNull View cartView, Context context) {
            super(cartView);
            imgItemCart = cartView.findViewById(R.id.imgItemCart);
            txtNameCart = cartView.findViewById(R.id.txtNameCart);
            txtPriceCart = cartView.findViewById(R.id.txtPriceCart);
            txtSoluongCart = cartView.findViewById(R.id.txtSoluongCart);
            cart_layout = cartView.findViewById(R.id.cart_layout);
            this.context = context;
        }

        public void bindData(ItemResponse itemResponse) {
            txtNameCart.setText(itemResponse.getName());
            txtPriceCart.setText(String.valueOf(itemResponse.getPrice()) + " đ");
            txtSoluongCart.setText(String.valueOf(itemResponse.getNumber()));

            Glide.with(context).load(itemResponse.getImg()).into(imgItemCart);
        }

    }
}
