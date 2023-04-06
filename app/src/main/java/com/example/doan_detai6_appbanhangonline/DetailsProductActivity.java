package com.example.doan_detai6_appbanhangonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.doan_detai6_appbanhangonline.Model.Product;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailsProductActivity extends AppCompatActivity {
    // KHAI BÁO BIẾN TOÀN CỤC
    TextView tvNameProduct, tvPriceProduct, tvSold, tvDescriptionProduct;
    Intent getDataIntent;
    Product product;
    String idProduct;
    NumberFormat numberFormat;

    // HÀM XỬ LÝ CHÍNH
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);
        numberFormat = NumberFormat.getInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(0);

        getDataIntent = getIntent();
        product = (Product) getDataIntent.getSerializableExtra("detailsProduct");
        idProduct = (String) getDataIntent.getSerializableExtra("idProduct");

        initUI();
        loadProduct();
        initListener();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    // ánh xạ view
    private void initUI() {
        tvNameProduct = findViewById(R.id.tvNameProduct);
        tvPriceProduct = findViewById(R.id.tvPriceProduct);
        tvSold = findViewById(R.id.tvSold);
        tvDescriptionProduct = findViewById(R.id.tvDescriptionProduct);
    }

    // xử lý sự kiển
    private void initListener() {

    }

    // render giao diện sản phẩm
    private void loadProduct() {
        tvNameProduct.setText(product.getName());

        tvPriceProduct.setText("đ" + numberFormat.format(product.getPrice()));

        String sold = "đã bán ";
        if (product.getSold() < 1000)
            sold += product.getSold();
        else
            sold += (product.getSold() / 1000) + "k";
        tvSold.setText(sold);

        tvDescriptionProduct.setText(product.getDescription());
    }
}