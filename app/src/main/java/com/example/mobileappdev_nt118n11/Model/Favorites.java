package com.example.mobileappdev_nt118n11.Model;

public class Favorites {
    private String Name, Image, Descr, Price, Foodtype;

    public Favorites() {
    }

    public Favorites(String foodName, String foodImage, String foodDescr, String foodPrice, String foodType) {
        this.Name = foodName;
        this.Image = foodImage;
        this.Descr = foodDescr;
        this.Price = foodPrice;
        this.Foodtype = foodType;
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
}
