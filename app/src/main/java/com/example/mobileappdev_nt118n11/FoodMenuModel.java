package com.example.mobileappdev_nt118n11;

public class FoodMenuModel {
    String imagelink;
    String name;
    String type;
    String price;
    String descpt;

    public FoodMenuModel(String imagelink, String name, String type, String price, String descpt) {
        this.imagelink = imagelink;
        this.name = name;
        this.type = type;
        this.price = price;
        this.descpt = descpt;
    }

    public String getImagelink() {
        return imagelink;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    public String getDescpt() {
        return descpt;
    }
}
