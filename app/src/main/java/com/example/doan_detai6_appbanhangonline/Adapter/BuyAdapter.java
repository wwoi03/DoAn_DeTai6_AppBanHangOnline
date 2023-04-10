package com.example.doan_detai6_appbanhangonline.Adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_detai6_appbanhangonline.Model.Cart;
import com.example.doan_detai6_appbanhangonline.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BuyAdapter extends RecyclerView.Adapter<BuyAdapter.BuyVH> {
    ArrayList<Cart> carts;
    Listener listener;

    public BuyAdapter(ArrayList<Cart> carts, Listener listener) {
        this.carts = carts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BuyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buy_product_row, parent, false);
        BuyVH buyVH = new BuyVH(view);
        return buyVH;
    }

    @Override
    public void onBindViewHolder(@NonNull BuyVH holder, int position) {
        Cart cart = carts.get(position);

        cart.getProduct().loadImage(holder.ivProduct);
        cart.getProduct().loadName(holder.tvNameProduct);
        cart.getProduct().loadPrice(holder.tvPrice);
        cart.loadQuantity(holder.tvQuantity);

        String totalProduct = "Tổng số tiền (" + cart.getQuantity() + " sản phẩm):";
        holder.tvTotalProduct.setText(totalProduct);

        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(0);
        double totalPrice = cart.getTotalPrice();
        String totalPriceString = "đ" + numberFormat.format(totalPrice);
        holder.tvTotalPrice.setText(totalPriceString);
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    class BuyVH extends RecyclerView.ViewHolder {
        TextView tvNameSupplier, tvNameProduct, tvPrice, tvQuantity, tvTotalProduct, tvTotalPrice;
        ImageView ivProduct;

        public BuyVH(@NonNull View itemView) {
            super(itemView);

            tvNameSupplier = itemView.findViewById(R.id.tvNameSupplier);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotalProduct = itemView.findViewById(R.id.tvTotalProduct);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            ivProduct = itemView.findViewById(R.id.ivProduct);
        }
    }

    public interface Listener {

    }
}
