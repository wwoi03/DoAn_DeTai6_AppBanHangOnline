package com.example.doan_detai6_appbanhangonline.Model;

public class Supplier {
    String id;
    String name;
    String phone;
    String address;
    String imageSupplier;

    public Supplier(String id, String name, String phone, String address, String imageSupplier) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.imageSupplier = imageSupplier;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageSupplier() {
        return imageSupplier;
    }

    public void setImageSupplier(String imageSupplier) {
        this.imageSupplier = imageSupplier;
    }
}
