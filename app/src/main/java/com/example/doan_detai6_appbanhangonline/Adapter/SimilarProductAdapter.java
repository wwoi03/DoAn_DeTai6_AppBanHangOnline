package com.example.doan_detai6_appbanhangonline.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_detai6_appbanhangonline.Extend.FirebaseStorageAuth;
import com.example.doan_detai6_appbanhangonline.Model.Product;
import com.example.doan_detai6_appbanhangonline.R;

import java.util.ArrayList;

public class SimilarProductAdapter extends RecyclerView.Adapter<SimilarProductAdapter.SimilarProductVH> {
    ArrayList<Product> products;
    Listener listener;

    public SimilarProductAdapter(ArrayList<Product> products, Listener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SimilarProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.similar_product_cell, parent, false);
        SimilarProductVH similarProductVH = new SimilarProductVH(view);
        return similarProductVH;
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarProductVH holder, int position) {
        Product product = products.get(position);

        FirebaseStorageAuth.loadImage("Products", product.getImageProduct(), holder.ivProduct);

        product.loadName(holder.tvNameProduct);
        product.loadPrice(holder.tvPrice);
        product.loadSold(holder.tvSold);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class SimilarProductVH extends RecyclerView.ViewHolder {
        ImageView ivProduct;
        TextView tvNameProduct, tvPrice, tvSold;

        public SimilarProductVH(@NonNull View itemView) {
            super(itemView);

            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvSold = itemView.findViewById(R.id.tvSold);
        }
    }

    public interface Listener {

    }
}
