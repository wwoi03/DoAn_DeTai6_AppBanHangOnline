package com.example.doan_detai6_appbanhangonline.Model;

public class Notification {
    String id;
    String idProduct;
    String description;

    public Notification(String id, String idProduct, String description) {
        this.id = id;
        this.idProduct = idProduct;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}