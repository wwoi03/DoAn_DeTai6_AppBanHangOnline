package com.example.doan_detai6_appbanhangonline.Model;


import java.io.Serializable;

public class Notification implements Serializable {
    String id;
    String idProduct;
    String title;
    String description;
    Product product;

    public Notification(String id, String idProduct, String title, String description, Product product) {
        this.id = id;
        this.idProduct = idProduct;
        this.title = title;
        this.description = description;
        this.product = product;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
