package com.example.doan_detai6_appbanhangonline;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    int position;
    public static int REQUEST_UPDATE_ORDER_DELIVERYADDRESS = 1040;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // sửa địa chỉ
                    if (result.getResultCode() == AddEditDeliveryAdressActivity.REQUEST_EDIT_ADDRESS) {
                        DeliveryAddress updateDA = (DeliveryAddress) result.getData().getSerializableExtra("updateDA");

                        if (updateDA.getRole() == 1 && deliveryAddresses.size() > 1) {
                            changeRole();
                        }

                        FirebaseFirestoreAuth.updateDeliveryAddress(updateDA);
                        initData();
                        Toast.makeText(AddressActivity.this, "Sửa thông tin địa chỉ thành công", Toast.LENGTH_SHORT).show();
                    }

                    // thêm địa chỉ
                    if (result.getResultCode() == AddEditDeliveryAdressActivity.REQUEST_ADD_ADDRESS) {
                        DeliveryAddress insertDA = (DeliveryAddress) result.getData().getSerializableExtra("insertDA");

                        if (insertDA.getRole() == 1 && deliveryAddresses.size() > 0) {
                            changeRole();
                        }

                        FirebaseFirestoreAuth.insertDeliveryAddresses(insertDA);
                        initData();
                        Toast.makeText(AddressActivity.this, "Thêm địa chỉ mới thành công", Toast.LENGTH_SHORT).show();
                    }

                    // xóa địa chỉ
                    if (result.getResultCode() == AddEditDeliveryAdressActivity.REQUEST_DELETE_ADDRESS) {
                        DeliveryAddress deleteDA = (DeliveryAddress) result.getData().getSerializableExtra("deleteDA");
                        FirebaseFirestoreAuth.deleteDeliveryAddresses(deleteDA);
                        initData();
                        Toast.makeText(AddressActivity.this, "Xóa địa chỉ thành công", Toast.LENGTH_SHORT).show();
                    }
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

        settingActionBar();
    }

    private void settingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Địa chỉ người nhận");
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
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
        deliveryAddresses = new ArrayList<>();
        deliveryAddressAdapter = new DeliveryAddressAdapter(deliveryAddresses, AddressActivity.this);
        FirebaseFirestoreAuth.getDeliveryAddresses(deliveryAddresses, deliveryAddressAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddressActivity.this, LinearLayoutManager.VERTICAL, false);
        rvAddresses.setLayoutManager(linearLayoutManager);
        rvAddresses.setAdapter(deliveryAddressAdapter);
    }

    // xử lý sự kiện
    private void initListener() {
        // xử lý bấm thêm ------------------------------------------ HÀO
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
    // xử lý khi bấm vào địa chỉ bất kỳ
    public void setOnClickDAListener(DeliveryAddress deliveryAddress, int pos) {
        position = pos;
        if (flag == 0) { // thay đổi địa chỉ đơn hàng - đang đứng ở trang order
            Intent intent = getIntent();
            intent.putExtra("deliveryAddress", deliveryAddress);
            setResult(REQUEST_UPDATE_ORDER_DELIVERYADDRESS ,intent);
            finish();
        } else if (flag == 1) { // chỉnh sửa địa chỉ - đang đứng ở thiết lập tài khoản hoặc trang AddressActivity
            Intent intent = new Intent(AddressActivity.this, AddEditDeliveryAdressActivity.class);
            intent.putExtra("deliveryAddress", deliveryAddress);
            intent.putExtra("flag", 0); // chỉnh sửa
            launcher.launch(intent);
        }
    }

    // xử lý khi cập nhât hoặc thêm địa chỉ mà có role == 1
    private void changeRole() {
        DeliveryAddress cr = new DeliveryAddress();
        int index = 0;
        for (int i = 0; i < deliveryAddresses.size(); i++) {
            if (deliveryAddresses.get(i).getRole() == 1) {
                deliveryAddresses.get(i).setRole(0);
                cr = deliveryAddresses.get(i);
                break;
            }
        }
        FirebaseFirestoreAuth.updateDeliveryAddress(cr);
    }
}