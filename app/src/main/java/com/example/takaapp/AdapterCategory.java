package com.example.takaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.takaapp.Dto.CategoryResponse;

import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CategoryResponse> list;
    public AdapterCategory(List<CategoryResponse> list) {
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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        ((ViewHolderCategory) viewHolder).bindData(list.get(i));

        final CategoryResponse categoryResponse = list.get(i);
        ((ViewHolderCategory) viewHolder).category_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(((ViewHolderCategory) viewHolder).context, categoryResponse.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolderCategory extends RecyclerView.ViewHolder {
        private Context context;
        ImageView imgCategory;
        TextView txtCategory;
        ConstraintLayout category_layout;

        public ViewHolderCategory(@NonNull View itemView, Context context) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.imgCategory);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            category_layout = itemView.findViewById(R.id.category_layout);
            this.context = context;
        }

        public void bindData(CategoryResponse categoryResponse) {
            txtCategory.setText(categoryResponse.getName());
            Glide.with(context).load(categoryResponse.getPic()).into(imgCategory);
        }
    }
}
