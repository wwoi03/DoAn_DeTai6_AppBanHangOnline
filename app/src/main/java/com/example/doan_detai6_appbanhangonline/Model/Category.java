package com.example.doan_detai6_appbanhangonline.Model;

public class Category {
    String id;
    String name;
    String imageCategory;

    public Category(String id, String name, String imageCategory) {
        this.id = id;
        this.name = name;
        this.imageCategory = imageCategory;
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

    public String getImageCategory() {
        return imageCategory;
    }

    public void setImageCategory(String imageCategory) {
        this.imageCategory = imageCategory;
    }
}
