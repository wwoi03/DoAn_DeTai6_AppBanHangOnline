package com.example.doan_detai6_appbanhangonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.doan_detai6_appbanhangonline.Adapter.BuyAdapter;
import com.example.doan_detai6_appbanhangonline.Model.Cart;

import java.util.ArrayList;
import java.util.List;

public class PayActivity extends AppCompatActivity {
    ArrayList<Cart> carts;
    BuyAdapter buyAdapter;
    RecyclerView rvBuyProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);


    }

    // ánh xạ view
    private void initUI() {

    }

    // xử lý sự kiện
    private void initListener() {

    }

    // lấy dữ liệu
    private void initData() {

    }
}