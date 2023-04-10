package com.example.doan_detai6_appbanhangonline.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_detai6_appbanhangonline.Model.Product;
import com.example.doan_detai6_appbanhangonline.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductVH> {
    ArrayList<Product> products;
    Listener listener;

    public ProductAdapter(ArrayList<Product> products, Listener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_cell, parent, false);
        ProductVH productVH = new ProductVH(view);
        return productVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductVH holder, int position) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(0);

        Product product = products.get(position);

        product.loadImage(holder.ivProduct);
        product.loadName(holder.tvNameProduct);
        product.loadPrice(holder.tvPrice);
        product.loadSold(holder.tvSold);

        initListener(holder, product, product.getId(), position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    private void initListener(ProductVH holder, Product product, String id, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnClickItemListener(product, id);
            }
        });
    }

    class ProductVH extends RecyclerView.ViewHolder {
        ImageView ivProduct;
        TextView tvNameProduct, tvPrice, tvSold;

        public ProductVH(@NonNull View itemView) {
            super(itemView);

            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvSold = itemView.findViewById(R.id.tvSold);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            if (getAdapterPosition() % 2 != 0) {
                params.leftMargin = itemView.getResources().getDimensionPixelOffset(R.dimen.dp_8);
                params.rightMargin = itemView.getResources().getDimensionPixelOffset(R.dimen.dp_4);
            } else {
                params.leftMargin = itemView.getResources().getDimensionPixelOffset(R.dimen.dp_4);
                params.rightMargin = itemView.getResources().getDimensionPixelOffset(R.dimen.dp_8);
            }
        }
    }

    public interface Listener {
        void setOnClickItemListener(Product product, String id);
    }
}
