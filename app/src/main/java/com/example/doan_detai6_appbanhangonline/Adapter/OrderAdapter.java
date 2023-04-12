package com.example.doan_detai6_appbanhangonline.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_detai6_appbanhangonline.Model.Order;
import com.example.doan_detai6_appbanhangonline.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderVH> {
    ArrayList<Order> orders;
    Listener listener;

    public OrderAdapter(ArrayList<Order> orders, Listener listener) {
        this.orders = orders;
        this.listener = listener;
    }


    @NonNull
    @Override
    public OrderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row, parent, false);
        return new OrderVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderVH holder, int position) {
        Order order = orders.get(position);

        order.getProduct().loadImage(holder.ivProduct);
        order.getProduct().loadName(holder.tvNameProduct);
        order.loadQuantity(holder.tvQuantity);
        order.getProduct().loadPrice(holder.tvPrice);
        holder.tvQuantity2.setText(order.getQuantity() + " sản phẩm");
        order.loadTotal(holder.tvTotalPrice);
        order.loadStatus(holder.tvStatus);

        initListener(holder, order, position);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    // xử lý sự kiện
    private void initListener(OrderVH holder, Order order, int position) {
        // xử lý khi bấn vào một đơn hàng bất kỳ
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnClickOrderListener(order, position);
            }
        });
    }

    class OrderVH extends RecyclerView.ViewHolder {
        ImageView ivProduct;
        TextView tvNameProduct, tvQuantity, tvPrice, tvQuantity2, tvTotalPrice, tvStatus;

        public OrderVH(@NonNull View itemView) {
            super(itemView);

            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity2 = itemView.findViewById(R.id.tvQuantity2);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }

    public interface Listener {
        void setOnClickOrderListener(Order order, int pos);
    }
}
