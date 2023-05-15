package com.example.doan_detai6_appbanhangonline;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.doan_detai6_appbanhangonline.Adapter.MyFavoriteProductAdapter;
import com.example.doan_detai6_appbanhangonline.Adapter.ProductAdapter;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.example.doan_detai6_appbanhangonline.Model.Product;

import java.util.ArrayList;

public class MyFavoriteProductActivity extends AppCompatActivity implements MyFavoriteProductAdapter.Listener {

    RecyclerView rvMFP;
    MyFavoriteProductAdapter myFavoriteProductAdapter;
    ArrayList<Product> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite_product);

        initUI();
        initData();
        initListener();

        settingActionBar();
    }

    private void settingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Sản phẩm yêu thích");
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void initUI() {
        rvMFP = findViewById(R.id.rvMFP);
    }

    private void initData() {
        products = new ArrayList<>();
        myFavoriteProductAdapter = new MyFavoriteProductAdapter(products, MyFavoriteProductActivity.this);
        FirebaseFirestoreAuth.getMyFavoriteProducts(products, myFavoriteProductAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyFavoriteProductActivity.this, LinearLayoutManager.VERTICAL, false);
        rvMFP.setLayoutManager(linearLayoutManager);
        rvMFP.setAdapter(myFavoriteProductAdapter);
    }

    private void initListener() {

    }

    @Override
    public void setOnClickItemListener(Product product, String id) {
        Intent intent = new Intent(MyFavoriteProductActivity.this, DetailsProductActivity.class);
        intent.putExtra("detailsProduct", product);
        intent.putExtra("idProduct", id);
        startActivity(intent);
    }
}