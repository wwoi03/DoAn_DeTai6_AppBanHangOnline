package com.example.doan_detai6_appbanhangonline.Model;

public class MyFavoriteProduct {
    String id;
    String idAccount;
    String idProduct;

    public MyFavoriteProduct(String id, String idAccount, String idProduct) {
        this.id = id;
        this.idAccount = idAccount;
        this.idProduct = idProduct;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }
}
