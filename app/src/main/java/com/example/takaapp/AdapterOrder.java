package com.example.takaapp;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.takaapp.Dto.ItemResponse;
import com.example.takaapp.Dto.OrderResponse;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<OrderResponse> list;
    private AdapterOrder.OnItemClick onOrderClick;

    public interface OnItemClick {
        void onOrderClick(OrderResponse orderResponse);
    }

    public AdapterOrder(List<OrderResponse> list, AdapterOrder.OnItemClick onOrderClick) {
        this.list = list;
        this.onOrderClick = onOrderClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_detail, viewGroup, false);
        return new ViewHolderOrder(view, viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((AdapterOrder.ViewHolderOrder) viewHolder).bindData(list.get(i));

        final OrderResponse orderResponse = list.get(i);
        ((ViewHolderOrder) viewHolder).order_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOrderClick.onOrderClick(orderResponse);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolderOrder extends RecyclerView.ViewHolder {

        private Context context;
        TextView txtOrderCode, txtOrderDate, txtOrderStatus;
        ConstraintLayout order_layout;
        ImageView imgStatus;

        public ViewHolderOrder(@NonNull View orderView, Context context) {
            super(orderView);

            txtOrderCode = orderView.findViewById(R.id.txtOrderCode);
            txtOrderDate = orderView.findViewById(R.id.txtOrderDate);
            txtOrderStatus = orderView.findViewById(R.id.txtOrderStatus);
            order_layout = orderView.findViewById(R.id.order_layout);
            imgStatus = orderView.findViewById(R.id.imgStatus);
            this.context = context;
        }

        public void bindData(OrderResponse orderResponse) {
            txtOrderCode.setText("Mã đơn hàng: "+orderResponse.get_id());

            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm dd/MM/yyyy");
            String date = formatter.format(orderResponse.getDate());


            txtOrderDate.setText("Ngày đặt hàng: "+date);

            String status = "";
            int img = 0;

            if(orderResponse.getStatus().equals("ORDERED")){
                status = "Đã đặt hàng";
                img = R.drawable.order_ordered;
            }

            if(orderResponse.getStatus().equals("SHIPPING")){
                status = "Đang giao hàng";
                img = R.drawable.order_ship;
            }

            if(orderResponse.getStatus().equals("DONE")){
                status = "Giao hàng thành công";
                img = R.drawable.order_success;
            }

            if(orderResponse.getStatus().equals("CANECLED")){
                status = "Đã hủy";
                img = R.drawable.order_fail;
            }

            System.out.println("test: "+status);


            txtOrderStatus.setText("Trạng Thái: "+status);

            imgStatus.setImageResource(img);

        }
    }
}
