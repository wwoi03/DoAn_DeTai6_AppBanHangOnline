package com.example.doan_detai6_appbanhangonline.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_detai6_appbanhangonline.Model.DeliveryAddress;
import com.example.doan_detai6_appbanhangonline.R;

import java.util.ArrayList;

public class DeliveryAddressAdapter extends RecyclerView.Adapter<DeliveryAddressAdapter.DeliveryAddressVH> {
    ArrayList<DeliveryAddress> deliveryAddresses;
    Listener listener;

    public DeliveryAddressAdapter(ArrayList<DeliveryAddress> deliveryAddresses, Listener listener) {
        this.deliveryAddresses = deliveryAddresses;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DeliveryAddressVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_row, parent, false);
        return new DeliveryAddressVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryAddressVH holder, int position) {
        DeliveryAddress deliveryAddress = deliveryAddresses.get(position);

        holder.tvName.setText("Tên người nhận: " + deliveryAddress.getName());
        holder.tvPhone.setText("Số điện thoại: " + deliveryAddress.getPhone());
        holder.tvAddress.setText("Địa chỉ: " + deliveryAddress.getAddress());
        if (deliveryAddress.getRole() == 1)
            holder.tvDefault.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return deliveryAddresses.size();
    }

    class DeliveryAddressVH extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvAddress, tvDefault;

        public DeliveryAddressVH(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDefault = itemView.findViewById(R.id.tvDefault);
        }
    }

    public interface Listener {

    }
}
