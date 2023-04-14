package com.example.doan_detai6_appbanhangonline;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.doan_detai6_appbanhangonline.Extend.Config;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.example.doan_detai6_appbanhangonline.Model.DeliveryAddress;

public class AddEditDeliveryAdressActivity extends AppCompatActivity {

    // View
    EditText etName, etPhone, etAddress;
    Switch swDefault;
    Button btDelete, btSuccess;
    DeliveryAddress deliveryAddress;
    int flag;
    Config config;
    public static int REQUEST_EDIT_ADDRESS = 1010;
    public static int REQUEST_ADD_ADDRESS = 1020;
    public static int REQUEST_DELETE_ADDRESS = 1030;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_delivery_adress);

        config = new Config(AddEditDeliveryAdressActivity.this);

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

        settingActionBar();
    }

    private void settingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
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
                Intent intent = getIntent();
                intent.putExtra("deleteDA", deliveryAddress);
                setResult(REQUEST_DELETE_ADDRESS, intent);
                finish();
            }
        });

        // xử lý bấm hoàn thành
        btSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    updateDA();
                } else {
                    insertDA();
                }
                finish();
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

    // Cập nhật
    private void updateDA() {
        String id = deliveryAddress.getId();
        String idAccount = deliveryAddress.getIdAccount();
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String address = etAddress.getText().toString();
        int role = swDefault.isChecked() == true ? 1 : 0;
        DeliveryAddress updateDA = new DeliveryAddress(id, idAccount, name, phone, address, role);

        Intent intent = getIntent();
        intent.putExtra("updateDA", updateDA);
        setResult(REQUEST_EDIT_ADDRESS, intent);
    }

    // Thêm
    private void insertDA() {
        String id = "";
        String idAccount = config.getIdAccount();
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String address = etAddress.getText().toString();
        int role = swDefault.isChecked() == true ? 1 : 0;
        DeliveryAddress insertDA = new DeliveryAddress(id, idAccount, name, phone, address, role);

        Intent intent = getIntent();
        intent.putExtra("insertDA", insertDA);
        setResult(REQUEST_ADD_ADDRESS, intent);
    }
}