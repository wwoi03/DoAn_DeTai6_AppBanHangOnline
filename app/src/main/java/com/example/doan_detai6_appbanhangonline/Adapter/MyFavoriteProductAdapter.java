package com.example.doan_detai6_appbanhangonline.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_detai6_appbanhangonline.Model.MyFavoriteProduct;
import com.example.doan_detai6_appbanhangonline.Model.Product;
import com.example.doan_detai6_appbanhangonline.R;

import java.util.ArrayList;

public class MyFavoriteProductAdapter extends RecyclerView.Adapter<MyFavoriteProductAdapter.MyFavoriteProductVH> {
    ArrayList<Product> products;
    Listener listener;

    public MyFavoriteProductAdapter(ArrayList<Product> products, Listener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyFavoriteProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_favorite_product_row, parent, false);
        return new MyFavoriteProductVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFavoriteProductVH holder, int position) {
        Product product = products.get(position);

        product.loadImage(holder.ivProduct);
        product.loadName(holder.tvName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnClickItemListener(product, product.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    class MyFavoriteProductVH extends RecyclerView.ViewHolder {
        ImageView ivProduct;
        TextView tvName;

        public MyFavoriteProductVH(@NonNull View itemView) {
            super(itemView);

            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }

    public interface Listener {
        void setOnClickItemListener(Product product, String id);
    }
}
