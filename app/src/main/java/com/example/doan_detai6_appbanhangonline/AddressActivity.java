package com.example.doan_detai6_appbanhangonline;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.doan_detai6_appbanhangonline.Adapter.DeliveryAddressAdapter;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.example.doan_detai6_appbanhangonline.Model.DeliveryAddress;

import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity implements DeliveryAddressAdapter.Listener {
    RecyclerView rvAddresses;
    DeliveryAddressAdapter deliveryAddressAdapter;
    ArrayList<DeliveryAddress> deliveryAddresses;
    LinearLayout llAddAddress;
    Intent getDataIntent;
    int flag;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );

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
        llAddAddress = findViewById(R.id.llAddAddress);
    }

    // lấy dữ liệu
    private void initData() {
        // Intent
        getDataIntent = getIntent();
        flag = (int) getDataIntent.getSerializableExtra("flag");

        // delivery address
        deliveryAddresses = FirebaseFirestoreAuth.deliveryAddresses;
        deliveryAddressAdapter = new DeliveryAddressAdapter(deliveryAddresses, AddressActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddressActivity.this, LinearLayoutManager.VERTICAL, false);
        rvAddresses.setLayoutManager(linearLayoutManager);
        rvAddresses.setAdapter(deliveryAddressAdapter);
    }

    // xử lý sự kiện
    private void initListener() {
        // xử lý bấm thêm
        llAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this, AddEditDeliveryAdressActivity.class);
                intent.putExtra("flag", 1); // thêm mới
                launcher.launch(intent);
            }
        });
    }

    @Override
    public void setOnClickDAListener(DeliveryAddress deliveryAddress, int pos) {
        if (flag == 0) {
            Intent intent = getIntent();
            intent.putExtra("deliveryAddress", deliveryAddress);
            setResult(1001 ,intent);
            finish();
        } else if (flag == 1) {
            Intent intent = new Intent(AddressActivity.this, AddEditDeliveryAdressActivity.class);
            intent.putExtra("deliveryAddress", deliveryAddress);
            intent.putExtra("flag", 0); // chỉnh sửa
            launcher.launch(intent);
        }
    }
}