package com.example.takaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.takaapp.Dto.CategoryResponse;
import com.example.takaapp.Dto.ItemResponse;

import java.util.List;

public class AdapterNew extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ItemResponse> list;
    private OnItemClick onNewClick;

    public interface OnItemClick {
        void onNewClick(ItemResponse itemResponse);
    }

    public AdapterNew(List<ItemResponse> list, OnItemClick onNewClick) {
        this.list = list;
        this.onNewClick = onNewClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_new, viewGroup, false);
        return new ViewHolderNew(view, viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((AdapterNew.ViewHolderNew) viewHolder).bindData(list.get(i));

        final ItemResponse itemResponse = list.get(i);
        ((ViewHolderNew) viewHolder).new_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNewClick.onNewClick(itemResponse);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolderNew extends RecyclerView.ViewHolder {
        private Context context;
        ImageView imgNew;
        TextView txtNameNew, txtPriceNew;
        ConstraintLayout new_layout;

        public ViewHolderNew(@NonNull View itemView, Context context) {
            super(itemView);
            imgNew = itemView.findViewById(R.id.imgNew);
            txtNameNew = itemView.findViewById(R.id.txtNameNew);
            txtPriceNew = itemView.findViewById(R.id.txtPriceNew);
            new_layout = itemView.findViewById(R.id.new_layout);
            this.context = context;
        }

        public void bindData(ItemResponse itemResponse) {
            txtNameNew.setText(itemResponse.getName());

            txtPriceNew.setText(String.valueOf(itemResponse.getPrice()) + " đ");

            Glide.with(context).load(itemResponse.getImg()).into(imgNew);
        }
    }
}
