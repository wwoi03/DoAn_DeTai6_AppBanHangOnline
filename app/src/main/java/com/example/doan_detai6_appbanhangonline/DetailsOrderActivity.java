package com.example.doan_detai6_appbanhangonline;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.example.doan_detai6_appbanhangonline.Model.DeliveryAddress;
import com.example.doan_detai6_appbanhangonline.Model.Order;

public class DetailsOrderActivity extends AppCompatActivity {

    Order order;
    // View
    TextView tvStatus, tvMessageStatus, tvNameAndPhone, tvUpdate, tvAddress, tvNameProduct, tvQuantity, tvPrice, tvTotalPrice, tvIdOrder, tvDateBuy, tvDateCancel;
    LinearLayout llCancelOrder;
    ImageView ivProduct, ivStatusImage;
    Button btOptionOrder;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),

            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Cập nhật địa chỉ giao hàng khác
                    if (result.getResultCode() == AddressActivity.REQUEST_UPDATE_ORDER_DELIVERYADDRESS) {
                        DeliveryAddress deliveryAddress = (DeliveryAddress) result.getData().getSerializableExtra("deliveryAddress");
                        FirebaseFirestoreAuth.updateOrder(order, deliveryAddress);
                        order.setRecipientAddress(deliveryAddress.getAddress());
                        order.setRecipientPhone(deliveryAddress.getPhone());
                        order.setRecipientName(deliveryAddress.getName());
                        loadOrder();
                        Toast.makeText(DetailsOrderActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_order);

        initUI();
        initData();
        initListener();
        loadOrder();

        settingActionBar();
    }

    private void settingActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Thông tin đơn hàng");
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    // ánh xạ view
    private void initUI() {
        tvStatus = findViewById(R.id.tvStatus);
        tvMessageStatus = findViewById(R.id.tvMessageStatus);
        tvNameAndPhone = findViewById(R.id.tvNameAndPhone);
        tvAddress = findViewById(R.id.tvAddress);
        tvNameProduct = findViewById(R.id.tvNameProduct);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvPrice = findViewById(R.id.tvPrice);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvIdOrder = findViewById(R.id.tvIdOrder);
        tvDateBuy = findViewById(R.id.tvDateBuy);
        ivProduct = findViewById(R.id.ivProduct);
        ivStatusImage = findViewById(R.id.ivStatusImage);
        btOptionOrder = findViewById(R.id.btOptionOrder);
        llCancelOrder = findViewById(R.id.llCancelOrder);
        tvDateCancel = findViewById(R.id.tvDateCancel);
        tvUpdate = findViewById(R.id.tvUpdate);
    }

    // get date
    private void initData() {
        Intent getDataOrder = getIntent();
        order = (Order) getDataOrder.getSerializableExtra("detailsOrder");
    }

    // xử lý sự kiện
    private void initListener() {
        // xử lý khi bấm vào option order
        btOptionOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status = order.getStatus();
                if (status == 0) { // xử lý khi bấm "Xác nhận hủy" - status == 0
                    FirebaseFirestoreAuth.cancelOrder(order);

                    Intent intent = new Intent(DetailsOrderActivity.this, MyOrderActivity.class);
                    intent.putExtra("fragment_to_show", "CF");
                    startActivity(intent);

                } else { // xử lý khi bấm "Mua lại" - status == 1 || status == 2
                    Intent intent = new Intent(DetailsOrderActivity.this, DetailsProductActivity.class);
                    intent.putExtra("detailsProduct", order.getProduct());
                    intent.putExtra("idProduct", order.getProduct().getId());
                    startActivity(intent);
                }
            }
        });

        // xử lý khi bấm cập nhật (Thay đổi địa chỉ đơn hàng)
        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsOrderActivity.this, AddressActivity.class);
                intent.putExtra("flag", 0);
                launcher.launch(intent);
            }
        });
    }

    // load order
    private void loadOrder() {
        loadStatus();
        loadStatusIamge();
        tvNameAndPhone.setText(order.getRecipientName() + "  |  (+84) " + order.getRecipientPhone());
        order.loadRecipientAddress(tvAddress);
        order.getProduct().loadImage(ivProduct);
        order.loadQuantity(tvQuantity);
        order.getProduct().loadPrice(tvPrice);
        order.loadTotal(tvTotalPrice);
        tvIdOrder.setText(order.getId());
        order.loadDateBuy(tvDateBuy);
        order.loadDateCancel(tvDateCancel);
        openContainerDateCancel();
        loadBtOption();
        loadUpdate();
    }

    // load tình trạng đơn hàng
    private void loadStatus() {
        int status = order.getStatus();
        String statusTitle, mesStatus;
        if (status == 0) {
            statusTitle = "Chờ thanh toán";
            mesStatus = "Đang chờ hệ thống xác nhận đơn hàng. Trong thời gian này, bạn có thể liên hệ với Người bán để xác nhận thêm thông tin đơn hàng nhé!";
        } else if (status == 1) {
            String deliveryDate = getDeliveryDate();
            statusTitle = "Đơn hàng đã được xác nhận";
            mesStatus = "Đơn vị vận chuyển đang lấy hàng. Bạn sẽ nhận được hàng muộn nhất vào ngày " + deliveryDate + ".";
        } else {
            statusTitle = "Đơn hàng đã hủy";
            mesStatus = "Bạn đã hủy đơn hàng này. Kiểm tra 'Chi tiết đơn hủy' để biết thêm thông tin chi tiết.";
        }
        tvStatus.setText(statusTitle);
        tvMessageStatus.setText(mesStatus);
    }

    // tính ngày giao
    private String getDeliveryDate() {
        String dateBuy = order.getDateBuy();
        String[] line = dateBuy.split("/");
        int day = Integer.parseInt(line[0]) + 7;
        int month = Integer.parseInt(line[1]);
        int year = Integer.parseInt(line[2]);
        if (day > 30) {
            day = day - 30;
            month++;
        }
        if (month > 12) {
            month = 1;
            year++;
        }
        String deliveryDate = String.format("%02d", day) + "/" + String.format("%02d", month) + "/" + year;
        return deliveryDate;
    }

    // load ảnh trạng thái
    private void loadStatusIamge() {
        int status = order.getStatus();
        if (status == 0) {
            ivStatusImage.setImageResource(R.drawable.baseline_payment_24);
        } else if (status == 1) {
            ivStatusImage.setImageResource(R.drawable.baseline_card_giftcard_24);
        } else {
            ivStatusImage.setImageResource(R.drawable.outline_cancel_presentation_24);
        }
    }

    // load button
    private void loadBtOption() {
        int status = order.getStatus();
        if (status == 0) {
            btOptionOrder.setText("Xác nhận hủy");
        } else {
            btOptionOrder.setText("Mua lại");
        }
    }

    // open date cancel
    private void openContainerDateCancel() {
        int status = order.getStatus();
        if (status == 2)
            llCancelOrder.setVisibility(View.VISIBLE);
    }

    // load update
    private void loadUpdate() {
        int status = order.getStatus();
        if (status == 0)
            tvUpdate.setVisibility(View.VISIBLE);
    }
}