package com.example.doan_detai6_appbanhangonline.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_detai6_appbanhangonline.Model.CustomerCare;
import com.example.doan_detai6_appbanhangonline.Model.Product;
import com.example.doan_detai6_appbanhangonline.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SuggestedProductsAdapter extends RecyclerView.Adapter<SuggestedProductsAdapter.SuggestedProductsVH> {
    ArrayList<Product> products;
    Listener listener;

    public SuggestedProductsAdapter(ArrayList<Product> products, Listener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SuggestedProductsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggested_products_cell, parent, false);
        SuggestedProductsVH  suggestedProductsVH = new SuggestedProductsVH(view);
        return suggestedProductsVH;
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestedProductsVH holder, int position) {
        Product product = products.get(position);

        holder.tvNameProduct.setText(product.getName());

        String price = "đ" + String.valueOf(product.getPrice());
        holder.tvPrice.setText(price);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class SuggestedProductsVH extends RecyclerView.ViewHolder {

        ImageView ivProduct;
        TextView tvNameProduct, tvPrice;

        public SuggestedProductsVH(@NonNull View itemView) {
            super(itemView);

            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct);
        }
    }

    interface Listener {

    }
}
