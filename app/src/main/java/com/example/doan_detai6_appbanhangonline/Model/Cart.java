package com.example.doan_detai6_appbanhangonline.Model;

public class Cart {
    String id;
    String idAccount;
    String idProduct;
    int quantity;

    public Cart(String id, String idAccount, String idProduct, int quantity) {
        this.id = id;
        this.idAccount = idAccount;
        this.idProduct = idProduct;
        this.quantity = quantity;
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
}
