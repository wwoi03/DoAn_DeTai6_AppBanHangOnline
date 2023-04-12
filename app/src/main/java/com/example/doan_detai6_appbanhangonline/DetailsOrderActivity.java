package com.example.doan_detai6_appbanhangonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan_detai6_appbanhangonline.Model.Order;

public class DetailsOrderActivity extends AppCompatActivity {

    Order order;
    Intent getDataOrder;

    // View
    TextView tvStatus, tvMessageStatus, tvNameAndPhone, tvAddress, tvNameProduct, tvQuantity, tvPrice, tvTotalPrice, tvIdOrder, tvDateBuy;
    ImageView ivProduct, ivStatusImage;
    Button btCancelOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_order);

        getDataOrder = getIntent();

        initUI();
        initData();
        loadOrder();

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
        btCancelOrder = findViewById(R.id.btCancelOrder);
    }

    // get date
    private void initData() {
        order = (Order) getDataOrder.getSerializableExtra("detailsOrder");
    }

    // load order
    private void loadOrder() {
        loadStatus();
        tvNameAndPhone.setText(order.getRecipientName() + "  |  (+84) " + order.getRecipientPhone());
        order.loadRecipientAddress(tvAddress);
        order.getProduct().loadImage(ivProduct);
        order.loadQuantity(tvQuantity);
        order.loadTotal(tvTotalPrice);
        tvIdOrder.setText(order.getId());
        order.loadDateBuy(tvDateBuy);
    }

    // load tình trạng đơn hàng
    private void loadStatus() {
        int status = order.getStatus();
        String statusTitle, mesStatus;
        if (status == 0) {
            statusTitle = "Chờ thanh toán";
            mesStatus = "Đang chờ hệ thống xác nhận đơn hàng. Trong thời gian này, bạn có thể liên hệ với Người bán để xác nhận thêm thông tin đơn hàng nhé!";
        } else if (status == 1) {
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
            statusTitle = "Đơn hàng đã được xác nhận";
            mesStatus = "Đơn vị vận chuyển đang lấy hàng. Bạn sẽ nhận được hàng muộn nhất vào ngày " + String.format("%02d", day) + "/" + String.format("%02d", month) + "/" + year + ".";
        } else {
            statusTitle = "Đơn hàng đã hủy";
            mesStatus = "Bạn đã hủy đơn hàng này. Kiểm tra 'Chi tiết đơn hủy' để biết thêm thông tin chi tiết.";
        }
        tvStatus.setText(statusTitle);
        tvMessageStatus.setText(mesStatus);
    }

    // load ảnh trạng thái
    private void loadStatusIamge() {

    }
}