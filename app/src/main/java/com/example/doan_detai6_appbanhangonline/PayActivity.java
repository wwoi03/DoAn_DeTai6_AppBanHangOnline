package com.example.doan_detai6_appbanhangonline;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    DeliveryAddress deliveryAddress;

    Intent buyCartsIntent;
    NumberFormat numberFormat;
    Config config;
    boolean checkDA = false;

    // View
    TextView tvDetailsTotalPrice, tvDetailsTotalPay, tvTotalPayPrice, tvNameAndPhone, tvAddress, tvDeliveryAddressNull;
    LinearLayout llInfoDelivery;
    Button btOrder;
    ImageButton ibAddress;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // cập nhật địa chỉ người nhận
                    if (result.getResultCode() == AddressActivity.REQUEST_UPDATE_ORDER_DELIVERYADDRESS) {
                        deliveryAddress = (DeliveryAddress) result.getData().getSerializableExtra("deliveryAddress");
                        loadDeliveryAddress();
                    }
                }
            }
    );

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
        getDeliveryAddress();

        settingActionBar();
    }

    private void settingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Thanh toán");
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
        ibAddress = findViewById(R.id.ibAddress);
        tvDeliveryAddressNull = findViewById(R.id.tvDeliveryAddressNull);
        llInfoDelivery = findViewById(R.id.llInfoDelivery);
    }

    // xử lý sự kiện
    private void initListener() {
        // xử lý khi bấm vào nút "Đặt hàng"
        btOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDA) {
                    for (int i = 0; i < carts.size(); i++) {
                        FirebaseFirestoreAuth.insertOrder(carts.get(i), deliveryAddress);
                        FirebaseFirestoreAuth.deleteCart(carts.get(i));

                        Intent intent = new Intent(PayActivity.this, MyOrderActivity.class);
                        intent.putExtra("fragment_to_show", "WFCF");
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(PayActivity.this, "Chưa có địa chỉ người nhận", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // xử lý khi bấm vào thay đổi địa chỉ nhận hàng
        ibAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayActivity.this, AddressActivity.class);
                intent.putExtra("flag", 0);
                launcher.launch(intent);
            }
        });
    }

    // lấy dữ liệu
    private void initData() {
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

    // load đỉa chị người nhận
    public void getDeliveryAddress() {
        deliveryAddress = new DeliveryAddress();
        FirebaseFirestoreAuth.db.collection("DeliveryAddress")
                .whereEqualTo("IdAccount", config.getIdAccount())
                .whereEqualTo("Role", 1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            deliveryAddress.setId(document.getId());
                            deliveryAddress.setName(document.get("Name").toString());
                            deliveryAddress.setPhone(document.get("Phone").toString());
                            deliveryAddress.setAddress(document.get("Address").toString());
                            deliveryAddress.setIdAccount(document.get("IdAccount").toString());

                            loadDeliveryAddress();
                        }
                    }
                });
        loadDeliveryAddress();
    }

    // load Địa chỉ nhận hàng
    private void loadDeliveryAddress() {
        if (deliveryAddress.getId() == "" || deliveryAddress.getId() == null) {
            checkDA = false;
            tvDeliveryAddressNull.setVisibility(View.VISIBLE);
            llInfoDelivery.setVisibility(View.GONE);
        } else {
            checkDA = true;
            tvDeliveryAddressNull.setVisibility(View.GONE);
            llInfoDelivery.setVisibility(View.VISIBLE);
            tvNameAndPhone.setText(deliveryAddress.getName() + "  |  (+84) " + deliveryAddress.getPhone());
            tvAddress.setText(deliveryAddress.getAddress());
        }
    }

    // load chi tiết hóa đơn
    public void loadDetailsOrder() {
        tvDetailsTotalPrice.setText("đ" + numberFormat.format(getTotalPrice()));
        tvDetailsTotalPay.setText("đ" + numberFormat.format(getTotalPrice()));
        tvTotalPayPrice.setText("đ" + numberFormat.format(getTotalPrice()));
    }
}