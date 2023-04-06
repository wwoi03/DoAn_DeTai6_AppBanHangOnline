package com.example.doan_detai6_appbanhangonline.Model;

import java.io.Serializable;

public class Product implements Serializable {
    String id;
    String name;
    double price;
    int sold;
    String description;
    String updateDay; // Ngày cập nhật
    String imageProduct;
    String idSupplier;
    String idCategory;

    public Product(String id, String name, double price, int sold, String description, String updateDay, String imageProduct, String idSupplier, String idCategory) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sold = sold;
        this.description = description;
        this.updateDay = updateDay;
        this.imageProduct = imageProduct;
        this.idSupplier = idSupplier;
        this.idCategory = idCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdateDay() {
        return updateDay;
    }

    public void setUpdateDay(String updateDay) {
        this.updateDay = updateDay;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public String getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(String idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }
}
