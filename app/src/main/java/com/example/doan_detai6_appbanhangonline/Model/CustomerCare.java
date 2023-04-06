package com.example.doan_detai6_appbanhangonline.Model;

public class CustomerCare {
    String id;
    String idAccount;
    String nameProduct;

    public CustomerCare(String id, String idAccount, String nameProduct) {
        this.id = id;
        this.idAccount = idAccount;
        this.nameProduct = nameProduct;
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

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }
}
