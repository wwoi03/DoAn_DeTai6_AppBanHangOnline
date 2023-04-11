package com.example.doan_detai6_appbanhangonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.doan_detai6_appbanhangonline.Adapter.DeliveryAddressAdapter;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.example.doan_detai6_appbanhangonline.Model.DeliveryAddress;

import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity implements DeliveryAddressAdapter.Listener {
    RecyclerView rvAddresses;
    DeliveryAddressAdapter deliveryAddressAdapter;
    ArrayList<DeliveryAddress> deliveryAddresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        initUI();
        initData();
        initListener();
    }

    // ánh xạ view
    private void initUI() {
        rvAddresses = findViewById(R.id.rvAddresses);
    }

    // lấy dữ liệu
    private void initData() {
        // delivery address
        deliveryAddresses = FirebaseFirestoreAuth.deliveryAddresses;
        deliveryAddressAdapter = new DeliveryAddressAdapter(deliveryAddresses, AddressActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddressActivity.this, LinearLayoutManager.VERTICAL, false);
        rvAddresses.setLayoutManager(linearLayoutManager);
        rvAddresses.setAdapter(deliveryAddressAdapter);
    }

    // xử lý sự kiện
    private void initListener() {

    }
}