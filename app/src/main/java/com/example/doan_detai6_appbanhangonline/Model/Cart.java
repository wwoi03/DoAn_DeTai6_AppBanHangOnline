package com.example.doan_detai6_appbanhangonline.Model;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Cart implements Serializable {
    String id;
    String idAccount;
    String idProduct;
    int quantity;
    String updateDay;
    Product product;
    public Cart(String id, String idAccount, String idProduct, int quantity, String updateDay, Product product) {
        this.id = id;
        this.idAccount = idAccount;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.updateDay = updateDay;
        this.product = product;
    }

    public Cart() {

    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUpdateDay() {
        return updateDay;
    }

    public void setUpdateDay(String updateDay) {
        this.updateDay = updateDay;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void loadQuantity(TextView textView) {
        textView.setText(String.valueOf(getQuantity()));
    }

    public double getTotalPrice() {
        return quantity * product.getPrice();
    }
}
