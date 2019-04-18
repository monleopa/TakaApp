package com.example.takaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.takaapp.Dto.ItemResponse;

import java.util.List;

public class AdapterItems extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ItemResponse> list;
    private Context context;

    public AdapterItems(Context context, List<ItemResponse> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolderItem(view, viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ViewHolderItem) viewHolder).bindData(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolderItem extends RecyclerView.ViewHolder {
        private Context context;
        ImageView imgItem;
        TextView txtNameitem, txtPriceitem;
        ConstraintLayout item_layout;

        public ViewHolderItem(@NonNull View itemView, Context context) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            txtNameitem = itemView.findViewById(R.id.txtNameitem);
            txtPriceitem = itemView.findViewById(R.id.txtPriceitem);
            item_layout = itemView.findViewById(R.id.item_layout);
            this.context = context;
        }

        public void bindData(ItemResponse itemResponse) {
            txtNameitem.setText(itemResponse.getName());
            txtPriceitem.setText(String.valueOf(itemResponse.getPrice()) + " Đ");

            Glide.with(context).load(itemResponse.getImg()).into(imgItem);
        }
    }
}
