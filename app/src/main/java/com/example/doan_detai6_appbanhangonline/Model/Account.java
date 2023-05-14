package com.example.doan_detai6_appbanhangonline.Model;

import android.widget.ImageView;

import com.example.doan_detai6_appbanhangonline.Extend.FirebaseStorageAuth;

import java.io.Serializable;

public class Account implements Serializable {
    String id;
    String email;
    String phone;
    String password;
    String name;
    String birthday;
    String gender;
    String address;
    String imageAccount;

    public Account() {
    }

    public Account(String id, String email, String phone, String password, String name, String birthday, String gender, String address, String imageAccount) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.imageAccount = imageAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageAccount() {
        return imageAccount;
    }

    public void setImageAccount(String imageAccount) {
        this.imageAccount = imageAccount;
    }

    public void loadImage(ImageView imageView) {
        FirebaseStorageAuth.loadImage("Accounts", getImageAccount(), imageView);
    }
}
