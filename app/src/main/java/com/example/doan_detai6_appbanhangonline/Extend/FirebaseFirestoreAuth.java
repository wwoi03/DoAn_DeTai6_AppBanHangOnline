package com.example.doan_detai6_appbanhangonline.Extend;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.doan_detai6_appbanhangonline.Model.Cart;
import com.example.doan_detai6_appbanhangonline.Model.DeliveryAddress;
import com.example.doan_detai6_appbanhangonline.Model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FirebaseFirestoreAuth {
    static Config config;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static ArrayList<DeliveryAddress> deliveryAddresses = new ArrayList<>();
    public static ArrayList<Product> products = new ArrayList<>();
    public static ArrayList<Cart> carts = new ArrayList<>();

    public FirebaseFirestoreAuth(Config config) {
        this.config = config;
        getProducts();
        getDeliveryAddresses();
    }

    /* ------------------------------- DELIVERY ADDRESS ------------------------------- */
    // lấy dữ liệu địa chỉ người nhận
    public static void getDeliveryAddresses() {
        db.collection("DeliveryAddress").whereEqualTo("IdAccount", config.getIdAccount())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("ABC", "OK DEE");
                            String id = document.getId();
                            String idAccount = document.get("IdAccount").toString();
                            String name = document.get("Name").toString();
                            String phone = document.get("Phone").toString();
                            String address = document.get("Address").toString();
                            int role = document.get("Role", Integer.class);
                            DeliveryAddress deliveryAddress = new DeliveryAddress(id, idAccount, name, phone, address, role);
                            deliveryAddresses.add(deliveryAddress);
                        }
                    }
                });
    }

    /* ------------------------------- PRODUCT ------------------------------- */
    // lấy dữ liệu từ Product
    public static void getProducts() {
        db.collection("Product")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String name = document.get("Name").toString();
                            double price = document.get("Price", Double.class);
                            int sold = document.get("Sold", Integer.class);
                            String description = document.get("Description").toString();
                            String updateDay = document.get("UpdateDay").toString(); // Ngày cập nhật
                            String imageProduct = document.get("ImageProduct").toString();
                            String idSupplier = document.get("IdSupplier").toString();
                            String idCategory = document.get("IdCategory").toString();
                            Product product = new Product(id, name, price, sold, description, updateDay, imageProduct, idSupplier, idCategory);
                            products.add(product);
                        }
                    }
                });
    }

    /* ------------------------------- CART ------------------------------- */
    // cập nhật cart
    public static void updateCart(Cart cart, int quantity) {
        Map<String, Object> newCart = new HashMap<>();
        newCart.put("IdAccount", cart.getIdAccount());
        newCart.put("IdProduct", cart.getIdProduct());
        newCart.put("UpdateDay", cart.getUpdateDay());
        newCart.put("Quantity", quantity);
        db.collection("Cart").document(cart.getId().trim())
                .set(newCart)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    // xóa cart
    public static void deleteCart(Cart cart) {
        db.collection("Cart").document(cart.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    /* ------------------------------- ORDER ------------------------------- */
    // Thêm order
    public static void insertOrder(Cart cart, DeliveryAddress deliveryAddress) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dateBuy = String.format("%02d", day) + "/" + String.format("%02d", month + 1) + "/" + year;

        Map<String, Object> newOrder = new HashMap<>();
        newOrder.put("IdProduct", cart.getProduct().getId());
        newOrder.put("IdAccount", config.getIdAccount());
        newOrder.put("Quantity", cart.getQuantity());
        newOrder.put("FeeShipping", 0);
        newOrder.put("Total", cart.getTotalPrice());
        newOrder.put("Status", 0);
        newOrder.put("RecipientPhone", deliveryAddress.getPhone());
        newOrder.put("RecipientName", deliveryAddress.getName());
        newOrder.put("RecipientAddress", deliveryAddress.getAddress());
        newOrder.put("DateBuy", dateBuy);
        newOrder.put("DateCancel", "");

        db.collection("Order").document()
                .set(newOrder)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    // cập nhật order
    public static void updateOrder(Cart cart, DeliveryAddress deliveryAddress) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dateBuy = String.format("%02d", day) + "/" + String.format("%02d", month + 1) + "/" + year;

        Map<String, Object> newOrder = new HashMap<>();
        newOrder.put("IdProduct", cart.getProduct().getId());
        newOrder.put("IdAccount", config.getIdAccount());
        newOrder.put("Quantity", cart.getQuantity());
        newOrder.put("FeeShipping", 0);
        newOrder.put("Total", cart.getTotalPrice());
        newOrder.put("Status", 0);
        newOrder.put("RecipientPhone", deliveryAddress.getPhone());
        newOrder.put("RecipientName", deliveryAddress.getName());
        newOrder.put("RecipientAddress", deliveryAddress.getAddress());
        newOrder.put("DateBuy", dateBuy);
        newOrder.put("DateCancel", "");

        db.collection("Order").document()
                .set(newOrder)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
}
