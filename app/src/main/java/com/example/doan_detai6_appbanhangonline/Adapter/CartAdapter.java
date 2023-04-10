package com.example.doan_detai6_appbanhangonline.Adapter;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.example.doan_detai6_appbanhangonline.Model.Cart;
import com.example.doan_detai6_appbanhangonline.Model.Product;
import com.example.doan_detai6_appbanhangonline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartVH> {
    ArrayList<Cart> carts;
    Listener listener;

    public CartAdapter(ArrayList<Cart> carts, Listener listener) {
        this.carts = carts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row, parent, false);
        CartVH cartVH = new CartVH(view);
        return cartVH;
    }

    @Override
    public void onBindViewHolder(@NonNull CartVH holder, int position) {
        Cart cart = carts.get(position);

        cart.loadQuantity(holder.etQuantity);
        cart.getProduct().loadName(holder.tvNameProduct);
        cart.getProduct().loadImage(holder.ivProduct);
        cart.getProduct().loadPrice(holder.tvPrice);

        initListener(holder, cart, cart.getId(), position);
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    private void initListener(CartVH holder, Cart cart, String id, int position) {
        // xử lý khi bấm vào một sản phẩm trong giỏ hàng
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnClickProductListener(cart.getProduct(), cart.getIdProduct());
            }
        });

        // xử lý khi bấm giảm số lượng
        holder.ibButtonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnClickBtDecreaseListener(cart, position, holder.etQuantity);
            }
        });

        // xử lý khi bấm tăng số lượng
        holder.ibButtonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnClickBtIncreaseListener(cart, position, holder.etQuantity);
            }
        });

        // xử lý khi nhập số lượng trong etQuantity
        holder.etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            // gọi trước khi text thay đổi
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            // gọi trong khi text thay đổi
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            // gọi sau khi text thay đổi
            public void afterTextChanged(Editable s) {
                listener.setOnTextChangedListener(cart, position, holder.etQuantity);
            }
        });

        // xử lý khi bấm vào nút checkbox
        holder.cbChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnClickCheckboxListener(cart, position, holder.cbChoose.isChecked());
            }
        });
    }

    class CartVH extends RecyclerView.ViewHolder {
        CheckBox cbChoose;
        ImageView ivProduct;
        TextView tvNameProduct, tvPrice;
        EditText etQuantity;
        ImageButton ibButtonDecrease, ibButtonIncrease;

        public CartVH(@NonNull View itemView) {
            super(itemView);

            cbChoose = itemView.findViewById(R.id.cbChoose);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            etQuantity = itemView.findViewById(R.id.etQuantity);
            ibButtonDecrease = itemView.findViewById(R.id.ibButtonDecrease);
            ibButtonIncrease = itemView.findViewById(R.id.ibButtonIncrease);
        }
    }

    public interface Listener {
        void setOnClickProductListener(Product product, String id);
        void setOnClickBtDecreaseListener(Cart cart, int pos, EditText etQuantity);
        void setOnClickBtIncreaseListener(Cart cart, int pos, EditText etQuantity);
        void setOnTextChangedListener(Cart cart, int pos, EditText etQuantity);
        void setOnClickCheckboxListener(Cart cart, int pos, boolean isChecked);
    }
}
