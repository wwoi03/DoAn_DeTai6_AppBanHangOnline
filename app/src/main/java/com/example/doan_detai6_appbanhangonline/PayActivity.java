package com.example.doan_detai6_appbanhangonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.doan_detai6_appbanhangonline.Adapter.BuyAdapter;
import com.example.doan_detai6_appbanhangonline.Extend.Config;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.example.doan_detai6_appbanhangonline.Model.Cart;
import com.example.doan_detai6_appbanhangonline.Model.DeliveryAddress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PayActivity extends AppCompatActivity implements BuyAdapter.Listener {
    ArrayList<Cart> carts;
    BuyAdapter buyAdapter;
    RecyclerView rvBuyProducts;
    Intent buyCartsIntent;
    TextView tvDetailsTotalPrice, tvDetailsTotalPay, tvTotalPayPrice, tvNameAndPhone, tvAddress;
    Button btOrder;
    Config config;
    ArrayList<DeliveryAddress> deliveryAddresses;
    DeliveryAddress deliveryAddress;
    NumberFormat numberFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        numberFormat = NumberFormat.getInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(0);

        config = new Config(PayActivity.this);
        buyCartsIntent = getIntent();

        initUI();
        initListener();
        initData();

        loadDetailsOrder();
        loadDeliveryAddress();

        settingActionBar();
    }

    private void settingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        actionBar.setTitle("Thanh toán");
        Spannable text = new SpannableString(actionBar.getTitle());
        text.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(text);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    // ánh xạ view
    private void initUI() {
        rvBuyProducts = findViewById(R.id.rvBuyProducts);
        tvTotalPayPrice = findViewById(R.id.tvTotalPayPrice);
        tvDetailsTotalPrice = findViewById(R.id.tvDetailsTotalPrice);
        tvDetailsTotalPay = findViewById(R.id.tvDetailsTotalPay);
        btOrder = findViewById(R.id.btOrder);
        tvNameAndPhone = findViewById(R.id.tvNameAndPhone);
        tvAddress = findViewById(R.id.tvAddress);
    }

    // xử lý sự kiện
    private void initListener() {
        // xử lý khi bấm vào nút "Đặt hàng"
        btOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < carts.size(); i++) {
                    FirebaseFirestoreAuth.insertOrder(carts.get(i), deliveryAddress);
                    FirebaseFirestoreAuth.deleteCart(carts.get(i));
                }
            }
        });
    }

    // lấy dữ liệu
    private void initData() {
        // deliveryAddresses
        deliveryAddresses = FirebaseFirestoreAuth.deliveryAddresses;

        // cart
        carts = (ArrayList<Cart>) buyCartsIntent.getSerializableExtra("buyCarts");
        buyAdapter = new BuyAdapter(carts, PayActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PayActivity.this, LinearLayoutManager.VERTICAL, false);
        rvBuyProducts.setLayoutManager(linearLayoutManager);
        rvBuyProducts.setAdapter(buyAdapter);
    }

    // tổng tiền
    public double getTotalPrice() {
        double total = 0;
        for (int i = 0; i < carts.size(); i++) {
            total += carts.get(i).getTotalPrice();
        }
        return total;
    }

    // thêm đơn hàng vào firebase
    public void insertOrder() {

    }

    // load đỉa chị người nhận
    public void loadDeliveryAddress() {
        deliveryAddress = new DeliveryAddress();
        for (int i = 0; i < deliveryAddresses.size(); i++) {
            if (deliveryAddresses.get(i).getRole() == 1) {
                deliveryAddress = deliveryAddresses.get(i);
            }
        }
        tvNameAndPhone.setText(deliveryAddress.getName() + "  |  (+84) " + deliveryAddress.getPhone());
        tvAddress.setText(deliveryAddress.getAddress());
        deliveryAddresses.add(deliveryAddress);
    }

    // load chi tiết hóa đơn
    public void loadDetailsOrder() {
        tvDetailsTotalPrice.setText("đ" + numberFormat.format(getTotalPrice()));
        tvDetailsTotalPay.setText("đ" + numberFormat.format(getTotalPrice()));
        tvTotalPayPrice.setText("đ" + numberFormat.format(getTotalPrice()));
    }
}