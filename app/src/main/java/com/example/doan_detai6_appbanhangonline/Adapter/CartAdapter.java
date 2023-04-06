package com.example.doan_detai6_appbanhangonline.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_detai6_appbanhangonline.Model.Product;
import com.example.doan_detai6_appbanhangonline.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartVH> {


    @NonNull
    @Override
    public CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CartVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class CartVH extends RecyclerView.ViewHolder {
        CheckBox cbChoose;
        ImageView ivProduct;
        TextView tvNameProduct, tvPrice;
        EditText etQuantity;

        public CartVH(@NonNull View itemView) {
            super(itemView);

            cbChoose = itemView.findViewById(R.id.cbChoose);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            etQuantity = itemView.findViewById(R.id.etQuantity);
        }
    }

    interface Listener {

    }
}
