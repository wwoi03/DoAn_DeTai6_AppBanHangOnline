package com.example.doan_detai6_appbanhangonline.Extend;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_detai6_appbanhangonline.DetailsProductActivity;
import com.example.doan_detai6_appbanhangonline.Model.Account;
import com.example.doan_detai6_appbanhangonline.Model.Cart;
import com.example.doan_detai6_appbanhangonline.Model.Category;
import com.example.doan_detai6_appbanhangonline.Model.DeliveryAddress;
import com.example.doan_detai6_appbanhangonline.Model.Notification;
import com.example.doan_detai6_appbanhangonline.Model.Order;
import com.example.doan_detai6_appbanhangonline.Model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FirebaseFirestoreAuth {
    static Config config;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static ArrayList<Product> products = new ArrayList<>();
    public static ArrayList<Cart> carts = new ArrayList<>();

    public FirebaseFirestoreAuth(Config config) {
        this.config = config;
    }

    public static String currentDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String cd = String.format("%02d", day) + "/" + String.format("%02d", month + 1) + "/" + year;
        return cd;
    }

    /* ------------------------------- DELIVERY ADDRESS ------------------------------- */
    // lấy dữ liệu địa chỉ người nhận
    public static void getDeliveryAddresses(ArrayList<DeliveryAddress> list, RecyclerView.Adapter adapter) {
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
                            list.add(deliveryAddress);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    // cập nhật địa chỉ
    public static void updateDeliveryAddress(DeliveryAddress deliveryAddresses) {
        Map<String, Object> updateDeliveryAddress = new HashMap<>();
        updateDeliveryAddress.put("Address", deliveryAddresses.getAddress());
        updateDeliveryAddress.put("IdAccount", deliveryAddresses.getIdAccount());
        updateDeliveryAddress.put("Name", deliveryAddresses.getName());
        updateDeliveryAddress.put("Phone", deliveryAddresses.getPhone());
        updateDeliveryAddress.put("Role", deliveryAddresses.getRole());

        db.collection("DeliveryAddress")
                .document(deliveryAddresses.getId().trim())
                .set(updateDeliveryAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
    // Thêm địa chỉ
    public static void insertDeliveryAddresses(DeliveryAddress deliveryAddresses) {
        Map<String, Object> updateDeliveryAddress = new HashMap<>();
        updateDeliveryAddress.put("Address", deliveryAddresses.getAddress());
        updateDeliveryAddress.put("IdAccount", deliveryAddresses.getIdAccount());
        updateDeliveryAddress.put("Name", deliveryAddresses.getName());
        updateDeliveryAddress.put("Phone", deliveryAddresses.getPhone());
        updateDeliveryAddress.put("Role", deliveryAddresses.getRole());

        db.collection("DeliveryAddress")
                .document()
                .set(updateDeliveryAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    public static void deleteDeliveryAddresses(DeliveryAddress deliveryAddresses) {
        db.collection("DeliveryAddress")
                .document(deliveryAddresses.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
    }

    /* ------------------------------- PRODUCT ------------------------------- */
    // lấy dữ liệu từ Product
    public static void getProducts(ArrayList<Product> list,RecyclerView.Adapter adapter) {
        products.clear();
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
                            list.add(product);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }


    // Load sản phẩm theo danh mục (Product_category)
    public static void getProductCategory(ArrayList<Product> list , String _idCategory, RecyclerView.Adapter adapter) {
        db.collection("Product").whereEqualTo("IdCategory", _idCategory)
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
                            list.add(product);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    // similar products
    /*public static void loadSimilarProducts(ArrayList<Product> list, Product product, RecyclerView.Adapter adapter) {
        db.collection("Product").whereEqualTo("IdCategory", product.getIdCategory().trim())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("ABC", document.getId());
                            String id = document.getId();
                            String name = document.get("Name").toString();
                            double price = document.get("Price", Double.class);
                            int sold = document.get("Sold", Integer.class);
                            String description = document.get("Description").toString();
                            String updateDay = document.get("UpdateDay").toString();
                            String imageProduct = document.get("ImageProduct").toString();
                            String idSupplier = document.get("IdSupplier").toString();
                            String idCategory = document.get("IdCategory").toString();
                            Product similarProduct = new Product(id, name, price, sold, description, updateDay, imageProduct, idSupplier, idCategory);
                            list.add(similarProduct);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }*/

    // tìm kiếm sản phẩm
    public static void getSearchProducts(ArrayList<Product> list, RecyclerView.Adapter adapter, String searchString, int flag) {
        String field = "";
        Query.Direction descending = Query.Direction.ASCENDING;

        if (flag == 0) {
            field = "Name";
        } else if (flag == 1) {
            field = "Price";
            descending = Query.Direction.DESCENDING;
        } else if (flag == 2){
            field = "Price";
        } else {
            field = "Sold";
            descending = Query.Direction.DESCENDING;
        }

        db.collection("Product")
                .orderBy(field, descending)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.get("Name").toString().toLowerCase().contains(searchString)) {
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
                                list.add(product);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    /* ------------------------------- CART ------------------------------- */
    // thêm product vào cart trên Firebase
    public static void insertCart(Product productCart, Context context) {
        Map<String, Object> newCart = new HashMap<>();
        newCart.put("IdAccount", config.getIdAccount());
        newCart.put("IdProduct", productCart.getId());
        newCart.put("UpdateDay", currentDay());
        newCart.put("Quantity", 1);
        FirebaseFirestoreAuth.db.collection("Cart").document()
                .set(newCart)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Thêm sản phẩm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //Them cart
    public static void insertCartAfterDelete(Cart cart, int quantity) {
        Map<String, Object> newCart = new HashMap<>();
        newCart.put("IdAccount", cart.getIdAccount());
        newCart.put("IdProduct", cart.getIdProduct());
        newCart.put("UpdateDay", cart.getUpdateDay());
        newCart.put("Quantity", quantity);
        db.collection("Cart").document(cart.getId())
                .set(newCart)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

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
        db.collection("Cart")
                .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (document.getId().equals(cart.getId()))
                                        db.collection("Cart")
                                                .document(cart.getId())
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {

                                                    }
                                                });
                                    }
                                }
                        });

    }

    // lấy dữ liệu từ giỏ hàng
    public static void getCarts(ArrayList<Cart> list, RecyclerView.Adapter adapter) {
        carts.clear();
        db.collection("Cart").whereEqualTo("IdAccount", config.getIdAccount())

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String idAccount = document.get("IdAccount").toString();
                            String idProduct = document.get("IdProduct").toString();
                            int quantity = document.get("Quantity", Integer.class);
                            String updateDay = document.get("UpdateDay").toString();

                            Product product = new Product();
                            for (int i = 0; i < products.size(); i++) {
                                if (idProduct.equals(products.get(i).getId())) {
                                    product = products.get(i);
                                    break;
                                }
                            }

                            Cart cart = new Cart(id, idAccount, idProduct, quantity, updateDay, product);
                            carts.add(cart);
                            list.add(cart);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    /* ------------------------------- ORDER ------------------------------- */
    // Thêm order
    public static void insertOrder(Cart cart, DeliveryAddress deliveryAddress) {
        String dateBuy = currentDay();

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

    public static void updateOrder(Order order, DeliveryAddress deliveryAddress) {
        Map<String, Object> updateOrder = new HashMap<>();
        updateOrder.put("IdProduct", order.getProduct().getId());
        updateOrder.put("IdAccount", order.getIdAccount());
        updateOrder.put("Quantity", order.getQuantity());
        updateOrder.put("FeeShipping", 0);
        updateOrder.put("Total", order.getTotal());
        updateOrder.put("Status", order.getStatus());
        updateOrder.put("RecipientPhone", deliveryAddress.getPhone());
        updateOrder.put("RecipientName", deliveryAddress.getName());
        updateOrder.put("RecipientAddress", deliveryAddress.getAddress());
        updateOrder.put("DateBuy", order.getDateBuy());
        updateOrder.put("DateCancel", "");

        db.collection("Order")
                .document(order.getId().trim())
                .set(updateOrder)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    // hủy order
    public static void cancelOrder(Order order) {
        String dateCancel = currentDay();

        Map<String, Object> newOrder = new HashMap<>();
        newOrder.put("IdProduct", order.getProduct().getId());
        newOrder.put("IdAccount", config.getIdAccount());
        newOrder.put("Quantity", order.getQuantity());
        newOrder.put("FeeShipping", 0);
        newOrder.put("Total", order.getTotal());
        newOrder.put("Status", 2);
        newOrder.put("RecipientPhone", order.getRecipientPhone());
        newOrder.put("RecipientName", order.getRecipientName());
        newOrder.put("RecipientAddress", order.getRecipientAddress());
        newOrder.put("DateBuy", order.getDateBuy());
        newOrder.put("DateCancel", dateCancel);

        db.collection("Order").document(order.getId())
                .set(newOrder)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }

    // lấy các sản phẩm trong order
    public static void getOrders(ArrayList<Order> list, int _status, RecyclerView.Adapter adapter) {
        db.collection("Order").whereEqualTo("IdAccount", config.getIdAccount())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.get("Status", Integer.class) == _status) {
                                String id = document.getId();
                                String idProduct = document.get("IdProduct").toString();
                                String idAccount = document.get("IdAccount").toString();
                                String dateBuy = document.get("DateBuy").toString();
                                String dateCancel = document.get("DateCancel").toString();
                                int quantity = document.get("Quantity", Integer.class);
                                double feeShipping = document.get("FeeShipping", Double.class);
                                double total = document.get("Total", Double.class);
                                int status = document.get("Status", Integer.class);
                                String recipientPhone = document.get("RecipientPhone").toString();
                                String recipientName = document.get("RecipientName").toString();
                                String recipientAddress = document.get("RecipientAddress").toString();
                                Product product = new Product();
                                for (int i = 0; i < products.size(); i++) {
                                    if (idProduct.equals(products.get(i).getId())) {
                                        product = products.get(i);
                                        break;
                                    }
                                }
                                Order order = new Order(id, idProduct, idAccount, dateBuy, dateCancel, quantity, feeShipping, total, status, recipientPhone, recipientName, recipientAddress, product);
                                list.add(order);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    /* ------------------------------- NOTIFICATION ------------------------------- */
    public static void getNotifications(ArrayList<Notification> list, RecyclerView.Adapter adapter) {
        db.collection("Notification")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String title=document.get("Title").toString();
                            String idproduct=document.get("IdProduct").toString();
                            String description=document.get("Description").toString();

                            Product product = new Product();
                            for (int i = 0; i < products.size(); i++) {
                                if (idproduct.equals(products.get(i).getId())) {
                                    product = products.get(i);
                                    break;
                                }
                            }

                            Notification notification=new Notification(id,idproduct,title,description,product);
                            list.add(notification);
                        }

                        adapter.notifyDataSetChanged();
                    }
                });
    }

    /* ------------------------------- CATEGORY ------------------------------- */
    // Load danh mục sản phẩm (Category)
    public static void getCategories(ArrayList<Category> list, RecyclerView.Adapter adapter) {
        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String name = document.get("Name").toString();
                            String imageCategory = document.get("Image").toString();
                            Category category = new Category(id, name, imageCategory);
                            list.add(category);
                        }

                        adapter.notifyDataSetChanged();
                    }
                });
    }

    /* ------------------------------- Account ------------------------------- */
    // lấy dữ liệu tài khoản
    public static void getAccount(Account account) {
        db.collection("Account")
                .document(config.getIdAccount())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        account.setId(document.getId());
                        account.setImageAccount(document.get("Image").toString());
                        account.setAddress(document.get("Address").toString());
                        account.setName(document.get("Name").toString());
                        account.setBirthday(document.get("Birthday").toString());
                        account.setEmail("");
                        account.setPassword("");
                        account.setGender(document.get("Gender").toString());
                        account.setPhone(document.get("Phone").toString());
                    }
                });
    }
}
