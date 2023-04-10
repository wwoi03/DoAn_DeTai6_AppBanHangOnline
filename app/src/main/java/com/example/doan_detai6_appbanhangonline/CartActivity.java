package com.example.doan_detai6_appbanhangonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.doan_detai6_appbanhangonline.Adapter.CartAdapter;
import com.example.doan_detai6_appbanhangonline.Adapter.SimilarProductAdapter;
import com.example.doan_detai6_appbanhangonline.Extend.FirebaseFirestoreAuth;
import com.example.doan_detai6_appbanhangonline.Model.Cart;
import com.example.doan_detai6_appbanhangonline.Model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity implements CartAdapter.Listener {
    RecyclerView rvCarts;
    CartAdapter cartAdapter;
    ArrayList<Cart> carts;
    ArrayList<Product> products;
    ArrayList<Cart> buyCarts;
    SharedPreferences sharedPreferences;
    String fileName = "config";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        sharedPreferences = getSharedPreferences(fileName, MODE_PRIVATE);

        initUI();
        initData();
        initListener();
        loadProductsAll();
        loadCart();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    // ánh xạ view
    private void initUI() {
        rvCarts = findViewById(R.id.rvCarts);
    }

    // xử lý sự kiện
    private void initListener() {

    }

    // khởi tạo
    private void initData() {
        // product
        products = new ArrayList<>();

        // buyCart
        buyCarts = new ArrayList<>();

        // cart
        carts = new ArrayList<>();
        cartAdapter = new CartAdapter(carts, CartActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);
        rvCarts.setLayoutManager(linearLayoutManager);
        rvCarts.setAdapter(cartAdapter);
    }

    private void loadCart() {
        FirebaseFirestoreAuth.db.collection("Cart").whereEqualTo("IdAccount", sharedPreferences.getString("id", ""))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("ABC", "OK");
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
                        }
                        cartAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void loadProductsAll() {
        FirebaseFirestoreAuth.db.collection("Product")
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

    @Override
    public void setOnClickProductListener(Product product, String id) {
        Intent intent = new Intent(this, DetailsProductActivity.class);
        intent.putExtra("detailsProduct", product);
        intent.putExtra("idProduct", id);
        /*launcher.launch(intent);*/
        startActivity(intent);
    }

    @Override
    public void setOnClickBtDecreaseListener(Cart cart, int pos, EditText etQuantity) {
        int quantity = cart.getQuantity();
        if (quantity > 1) {
            quantity--;
        }
        cart.setQuantity(quantity);
        updateCart(cart, quantity);
        cart.loadQuantity(etQuantity);
    }

    @Override
    public void setOnClickBtIncreaseListener(Cart cart, int pos, EditText etQuantity) {
        int quantity = cart.getQuantity();
        quantity++;
        cart.setQuantity(quantity);
        updateCart(cart, quantity);
        cart.loadQuantity(etQuantity);
    }

    @Override
    public void setOnClickCheckboxListener(Cart cart, Product product, int pos, boolean isChecked) {
        if (isChecked == true) {

        }
    }

    public void updateCart(Cart cart, int quantity) {
        Map<String, Object> newCart = new HashMap<>();
        newCart.put("IdAccount", cart.getIdAccount());
        newCart.put("IdProduct", cart.getIdProduct());
        newCart.put("UpdateDay", cart.getUpdateDay());
        newCart.put("Quantity", quantity);
        FirebaseFirestoreAuth.db.collection("Cart").document(cart.getId().trim())
                .set(newCart)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
}