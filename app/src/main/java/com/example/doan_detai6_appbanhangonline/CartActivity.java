package com.example.doan_detai6_appbanhangonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_detai6_appbanhangonline.Adapter.CartAdapter;
import com.example.doan_detai6_appbanhangonline.Adapter.SimilarProductAdapter;
import com.example.doan_detai6_appbanhangonline.Extend.Config;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.example.doan_detai6_appbanhangonline.Model.Cart;
import com.example.doan_detai6_appbanhangonline.Model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class CartActivity extends AppCompatActivity implements CartAdapter.Listener {
    RecyclerView rvCarts;
    CartAdapter cartAdapter;
    ArrayList<Cart> carts;
    ArrayList<Product> products;
    ArrayList<Cart> buyCarts;
    CheckBox cbChooseAll;
    TextView tvTotalPrice;
    Button btBuy;
    Config config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        config = new Config(CartActivity.this);

        initUI();
        initData();
        initListener();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvCarts);

        settingActionBar();
    }

    private void settingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Giỏ hàng");
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    // ánh xạ view
    private void initUI() {
        rvCarts = findViewById(R.id.rvCarts);
        cbChooseAll = findViewById(R.id.cbChooseAll);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btBuy = findViewById(R.id.btBuy);
    }

    // xử lý sự kiện
    private void initListener() {
        // xử lý khi bấm vào mua
        btBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, PayActivity.class);
                intent.putExtra("buyCarts", buyCarts);
                startActivity(intent);
            }
        });
    }

    // khởi tạo
    private void initData() {
        // product
        products = FirebaseFirestoreAuth.products;

        // buyCart
        buyCarts = new ArrayList<>();

        // cart
        carts = new ArrayList<>();
        cartAdapter = new CartAdapter(carts, CartActivity.this);
        FirebaseFirestoreAuth.getCarts(carts, cartAdapter);
        /*getSupportActionBar().setTitle("Giỏ hàng (" + cartAdapter.getItemCount() + ")");*/
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);
        rvCarts.setLayoutManager(linearLayoutManager);
        rvCarts.setAdapter(cartAdapter);
    }

    @Override
    // xử lý khi bấm vào một sản phẩm bất kỳ
    public void setOnClickProductListener(Product product, String id) {
        Intent intent = new Intent(this, DetailsProductActivity.class);
        intent.putExtra("detailsProduct", product);
        intent.putExtra("idProduct", id);
        /*launcher.launch(intent);*/
        startActivity(intent);
    }

    @Override
    // xử lý ấn vào nút giảm số lượng
    public void setOnClickBtDecreaseListener(Cart cart, int pos, EditText etQuantity) {
        int quantity = cart.getQuantity();
        if (quantity > 1) {
            quantity--;
        }
        cart.setQuantity(quantity);
        FirebaseFirestoreAuth.updateCart(cart, quantity);
        cart.loadQuantity(etQuantity);
    }

    @Override
    // xử lý ấn vào nút tăng số lượng
    public void setOnClickBtIncreaseListener(Cart cart, int pos, EditText etQuantity) {
        int quantity = cart.getQuantity();
        quantity++;
        cart.setQuantity(quantity);
        FirebaseFirestoreAuth.updateCart(cart, quantity);
        cart.loadQuantity(etQuantity);
    }

    @Override
    // xử lý khi thay đổi thẻ EditText
    public void setOnTextChangedListener(Cart cart, int pos, EditText etQuantity) {
        int quantity = Integer.parseInt(etQuantity.getText().toString().equals("") ? "1" : etQuantity.getText().toString());
        cart.setQuantity(quantity);
        FirebaseFirestoreAuth.updateCart(cart, quantity);
    }

    @Override
    // xử lý tích checkbox sản phẩm
    public void setOnClickCheckboxListener(Cart cart, int pos, boolean isChecked) {
        if (isChecked == true) {
            buyCarts.add(cart);
        } else {
            buyCarts.remove(pos);
        }
    }

    Cart deleteProduct = null;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView
            int position = viewHolder.getAdapterPosition();
            deleteProduct= carts.get(position);


            FirebaseFirestoreAuth.deleteCart(deleteProduct);
            carts.remove(position);
            cartAdapter.notifyDataSetChanged();

            Snackbar snackbar = Snackbar
                    .make(rvCarts, "Bạn có chắc muốn xóa sản phẩm khỏi giỏ hàng", Snackbar.LENGTH_LONG);
            snackbar.setAction("Hoàn tác", new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    FirebaseFirestoreAuth.insertCartAfterDelete(deleteProduct, deleteProduct.getQuantity());
                    carts.add(deleteProduct);
                    cartAdapter.notifyDataSetChanged();
                }
            }).setBackgroundTint(Color.WHITE).setTextColor(Color.RED).setActionTextColor(Color.GREEN).show();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(CartActivity.this,R.color.delete_color))
                    .addSwipeLeftActionIcon(R.drawable.baseline_delete_forever_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}