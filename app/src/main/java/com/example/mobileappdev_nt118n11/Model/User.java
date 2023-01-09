package com.example.mobileappdev_nt118n11.Model;

public class User {
    private  String Name;
    private  String Password;
    private  String Gmail;
    private  String Address;
    public User() {
    }

    public User(String name, String password, String gmail, String address) {
        Name = name;
        Password = password;
        Gmail = gmail;
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }

    public String getGmail() {
        return Gmail;
    }

    public String getAddress() {
        return Address;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setGmail(String gmail) {
        Gmail = gmail;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
