package com.example.doan_detai6_appbanhangonline.Model;

import java.io.Serializable;

public class DeliveryAddress implements Serializable {
    String id;
    String idAccount;
    String name;
    String phone;
    String address;
    int role;

    public DeliveryAddress() {

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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public DeliveryAddress(String id, String idAccount, String name, String phone, String address, int role) {
        this.id = id;
        this.idAccount = idAccount;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }
}
