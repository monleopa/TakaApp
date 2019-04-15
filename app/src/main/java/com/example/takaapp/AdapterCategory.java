package com.example.takaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<User> list;

    public AdapterCategory(List<User> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false);
        return new ViewHolderCategory(view, viewGroup.getContext());
    }

    //
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ViewHolderCategory) viewHolder).bindData(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolderCategory extends RecyclerView.ViewHolder {
        private Context context;
        ImageView imageView;
        TextView txtCategory;

        public ViewHolderCategory(@NonNull View itemView, Context context) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgCategory);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            this.context = context;
        }

        public void bindData(User user) {
            txtCategory.setText(user.getName());
            Glide.with(context).load(user.getPhone()).into(imageView);
        }
    }
}
