package com.example.doan_detai6_appbanhangonline;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_detai6_appbanhangonline.Adapter.SimilarProductAdapter;
import com.example.doan_detai6_appbanhangonline.Extend.Config;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.example.doan_detai6_appbanhangonline.Model.Cart;
import com.example.doan_detai6_appbanhangonline.Model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DetailsProductActivity extends AppCompatActivity implements SimilarProductAdapter.Listener {
    // KHAI BÁO BIẾN TOÀN CỤC
    RecyclerView rvSimilarProducts;
    SimilarProductAdapter similarProductAdapter;
    ArrayList<Product> similarProducts;
    TextView tvNameProduct, tvPriceProduct, tvSold, tvDescriptionProduct;
    ImageView ivProduct;
    LinearLayout llAddCart, llBuyNow;
    Intent getDataIntent;
    Product product;
    String idProduct;
    ArrayList<Cart> buyCarts;
    Config config;

    // HÀM XỬ LÝ CHÍNH
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);

        config = new Config(DetailsProductActivity.this);

        getDataIntent = getIntent();
        product = (Product) getDataIntent.getSerializableExtra("detailsProduct");
        idProduct = (String) getDataIntent.getSerializableExtra("idProduct");

        initUI();
        initData();
        initListener();

        loadProduct();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    // ánh xạ view
    private void initUI() {
        rvSimilarProducts = findViewById(R.id.rvSimilarProducts);
        tvNameProduct = findViewById(R.id.tvNameProduct);
        tvPriceProduct = findViewById(R.id.tvPriceProduct);
        ivProduct = findViewById(R.id.ivProduct);
        tvSold = findViewById(R.id.tvSold);
        tvDescriptionProduct = findViewById(R.id.tvDescriptionProduct);
        llAddCart = findViewById(R.id.llAddCart);
        llBuyNow = findViewById(R.id.llBuyNow);
    }

    // xử lý sự kiển
    private void initListener() {
        // xử lý thêm sản phẩm vào giỏ hàng
        llAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestoreAuth.insertCart(product, DetailsProductActivity.this);
            }
        });

        // xử lý khi bấm vào nút "Mua ngay"
        llBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyCarts = new ArrayList<>();
                String id = "";
                String idAccount = config.getIdAccount();
                String idProduct = product.getId();
                int quantity = 1;
                String updateDay = FirebaseFirestoreAuth.currentDay();
                Cart cart = new Cart(id, idAccount, idProduct, quantity, updateDay, product);
                buyCarts.add(cart);

                Intent intent = new Intent(DetailsProductActivity.this, PayActivity.class);
                intent.putExtra("buyCarts", buyCarts);
                startActivity(intent);
            }
        });
    }

    // nạp dữ liệu
    private void initData() {
        // Similar products
        similarProducts = new ArrayList<>();
        similarProductAdapter = new SimilarProductAdapter(similarProducts, DetailsProductActivity.this);
        FirebaseFirestoreAuth.getProductCategory(similarProducts, product.getIdCategory(), similarProductAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailsProductActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvSimilarProducts.setLayoutManager(linearLayoutManager);
        rvSimilarProducts.setAdapter(similarProductAdapter);
    }

    // render giao diện sản phẩm
    private void loadProduct() {
        product.loadName(tvNameProduct);
        product.loadPrice(tvPriceProduct);
        product.loadSold(tvSold);
        product.loadDescription(tvDescriptionProduct);
        product.loadImage(ivProduct);
    }
}