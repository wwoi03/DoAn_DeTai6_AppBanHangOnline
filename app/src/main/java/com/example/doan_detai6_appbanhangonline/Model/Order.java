package com.example.doan_detai6_appbanhangonline.Model;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import com.example.doan_detai6_appbanhangonline.R;
import com.google.firebase.database.collection.LLRBNode;

import java.text.NumberFormat;
import java.util.Locale;

public class Order {
    String id;
    String idProduct;
    String idAccount;
    String dateBuy;
    String dateCancel;
    int quantity;
    double feeShipping;
    double total;
    int status;
    String recipientPhone;
    String recipientName;
    String recipientAddress;
    Product product;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getDateBuy() {
        return dateBuy;
    }

    public void setDateBuy(String dateBuy) {
        this.dateBuy = dateBuy;
    }

    public String getDateCancel() {
        return dateCancel;
    }

    public void setDateCancel(String dateCancel) {
        this.dateCancel = dateCancel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getFeeShipping() {
        return feeShipping;
    }

    public void setFeeShipping(double feeShipping) {
        this.feeShipping = feeShipping;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order(String id, String idProduct, String idAccount, String dateBuy, String dateCancel, int quantity, double feeShipping, double total, int status, String recipientPhone, String recipientName, String recipientAddress, Product product) {
        this.id = id;
        this.idProduct = idProduct;
        this.idAccount = idAccount;
        this.dateBuy = dateBuy;
        this.dateCancel = dateCancel;
        this.quantity = quantity;
        this.feeShipping = feeShipping;
        this.total = total;
        this.status = status;
        this.recipientPhone = recipientPhone;
        this.recipientName = recipientName;
        this.recipientAddress = recipientAddress;
        this.product = product;
    }

    public void loadQuantity(TextView textView) {
        textView.setText("x" + getQuantity());
    }

    public void loadTotal(TextView textView) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(0);

        textView.setText("đ" + numberFormat.format(getTotal()));
    }

    public void loadStatus(TextView textView) {
        String s;
        ColorDrawable color;
        if (status == 0) {
            color = new ColorDrawable(Color.parseColor("#C8C8C8"));
            s = "Đang xử lý";
        }
        else if (status == 1) {
            color = new ColorDrawable(Color.parseColor("#1FB141"));
            s = "Đã xác nhận";
        }
        else {
            color = new ColorDrawable(Color.parseColor("#EE4D2D"));
            s = "Đã hủy";
        }
        textView.setBackground(color);
        textView.setText(s);
    }
}