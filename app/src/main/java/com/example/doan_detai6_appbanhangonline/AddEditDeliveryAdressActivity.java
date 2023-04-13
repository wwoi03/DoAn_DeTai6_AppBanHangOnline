package com.example.doan_detai6_appbanhangonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.doan_detai6_appbanhangonline.Model.DeliveryAddress;

public class AddEditDeliveryAdressActivity extends AppCompatActivity {

    // View
    EditText etName, etPhone, etAddress;
    Switch swDefault;
    Button btDelete, btSuccess;
    DeliveryAddress deliveryAddress;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_delivery_adress);

        initData();
        initListener();

        Intent getDataIntent = getIntent();
        flag = (int) getDataIntent.getSerializableExtra("flag");


        if (flag == 0) {
            getSupportActionBar().setTitle("Sửa địa chỉ người nhận");
            deliveryAddress = (DeliveryAddress) getDataIntent.getSerializableExtra("deliveryAddress") ;
            loadDeliveryAddress();
        } else {
            getSupportActionBar().setTitle("Thêm địa chỉ người nhận");
            btDelete.setVisibility(View.GONE);
        }
    }

    // ánh xạ view
    private void initData() {
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        swDefault = findViewById(R.id.swDefault);
        btDelete = findViewById(R.id.btDelete);
        btSuccess = findViewById(R.id.btSuccess);
    }

    // xử lý sự kiện
    private void initListener() {
        // xử lý bấm xóa địa chỉ
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // xử lý bấm hoàn thành
        btSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    // load địa chỉ người nhận
    private void loadDeliveryAddress() {
        etName.setText(deliveryAddress.getName());
        etPhone.setText(deliveryAddress.getPhone());
        etAddress.setText(deliveryAddress.getAddress());
        if (deliveryAddress.getRole() == 1) {
            swDefault.setChecked(true);
        }
    }
}