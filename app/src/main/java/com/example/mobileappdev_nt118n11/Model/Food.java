package com.example.mobileappdev_nt118n11.Model;

public class Food {
    private String Id;
    private String Name, Image, Descr, Price, Foodtype;
    private int Quantity;
    public Food() {
    }

    public Food(String name, String image, String descr, String price, String foodtype) {
        Name = name;
        Image = image;
        Descr = descr;
        Price = price;
        Foodtype = foodtype;
        Id = null;
        Quantity = 0;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescr() {
        return Descr;
    }

    public void setDescr(String descr) {
        Descr = descr;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getFoodtype() {
        return Foodtype;
    }

    public void setFoodtype(String foodtype) {
        Foodtype = foodtype;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

}
