package com.example.doan_detai6_appbanhangonline.Model;

public class Order {
    String id;
    String idProduct;
    String idAccount;
    String dateBuy;
    int quantity;
    double feeShipping;
    double total;
    String status;

    public Order(String id, String idProduct, String idAccount, String dateBuy, int quantity, double feeShipping, double total, String status) {
        this.id = id;
        this.idProduct = idProduct;
        this.idAccount = idAccount;
        this.dateBuy = dateBuy;
        this.quantity = quantity;
        this.feeShipping = feeShipping;
        this.total = total;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}