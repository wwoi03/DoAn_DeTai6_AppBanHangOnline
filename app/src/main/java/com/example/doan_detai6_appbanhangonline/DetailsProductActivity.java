package com.example.doan_detai6_appbanhangonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_detai6_appbanhangonline.Adapter.SimilarProductAdapter;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
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
    LinearLayout llAddCart, llBuyNow;
    Intent getDataIntent;
    Product product;
    String idProduct;
    SharedPreferences sharedPreferences;
    FirebaseFirestore db;

    // HÀM XỬ LÝ CHÍNH
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);

        sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        getDataIntent = getIntent();
        product = (Product) getDataIntent.getSerializableExtra("detailsProduct");
        idProduct = (String) getDataIntent.getSerializableExtra("idProduct");

        db = FirebaseFirestore.getInstance();

        initUI();
        loadSimilarProducts();
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
                insertCart(product);
            }
        });
    }

    // nạp dữ liệu
    private void initData() {
        // Similar products
        similarProducts = new ArrayList<>();
        similarProductAdapter = new SimilarProductAdapter(similarProducts, DetailsProductActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailsProductActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvSimilarProducts.setLayoutManager(linearLayoutManager);
        rvSimilarProducts.setAdapter(similarProductAdapter);
    }

    // thêm product vào cart trên Firebase
    private void insertCart(Product productCart) {
        Map<String, Object> newCart = new HashMap<>();
        newCart.put("IdAccount", sharedPreferences.getString("id", ""));
        newCart.put("IdProduct", productCart.getId());
        newCart.put("UpdateDay", "");
        newCart.put("Quantity", 1);
        FirebaseFirestoreAuth.db.collection("Cart").document()
                .set(newCart)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(DetailsProductActivity.this, "Thêm sản phẩm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // render giao diện sản phẩm
    private void loadProduct() {
        product.loadName(tvNameProduct);
        product.loadPrice(tvPriceProduct);
        product.loadSold(tvSold);
        product.loadDescription(tvDescriptionProduct);
    }

    // similar products
    private void loadSimilarProducts() {
        db.collection("Product").whereEqualTo("IdCategory", product.getIdCategory().trim())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("ABC", document.getId());
                            String id = document.getId();
                            String name = document.get("Name").toString();
                            double price = document.get("Price", Double.class);
                            int sold = document.get("Sold", Integer.class);
                            String description = document.get("Description").toString();
                            String updateDay = document.get("UpdateDay").toString();
                            String imageProduct = document.get("ImageProduct").toString();
                            String idSupplier = document.get("IdSupplier").toString();
                            String idCategory = document.get("IdCategory").toString();
                            Product similarProduct = new Product(id, name, price, sold, description, updateDay, imageProduct, idSupplier, idCategory);
                            similarProducts.add(similarProduct);
                        }
                        similarProductAdapter.notifyDataSetChanged();
                    }
                });
    }
}