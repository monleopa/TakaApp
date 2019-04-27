package com.example.takaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
    private FragmentManager fragmentManager;
    private AdapterCategory.OnItemClick onItemClick;

    public AdapterItems(FragmentManager fragmentManager, List<ItemResponse> list) {
        this.list = list;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolderItem(view, viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        ((ViewHolderItem) viewHolder).bindData(list.get(i));

        final ItemResponse itemResponse = list.get(i);
        ((AdapterItems.ViewHolderItem) viewHolder).item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container, DetailItemFrangment.newInstance(itemResponse));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
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
            imgItem = itemView.findViewById(R.id.imgItemCart);
            txtNameitem = itemView.findViewById(R.id.txtNameCart);
            txtPriceitem = itemView.findViewById(R.id.txtPriceCart);
            item_layout = itemView.findViewById(R.id.item_layout);
            this.context = context;
        }

        public void bindData(ItemResponse itemResponse) {
            txtNameitem.setText(itemResponse.getName());
            txtPriceitem.setText(String.valueOf(itemResponse.getPrice()) + " đ");

            Glide.with(context).load(itemResponse.getImg()).into(imgItem);
        }
    }
}
