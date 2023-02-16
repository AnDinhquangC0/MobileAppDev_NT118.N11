package com.example.mobileappdev_nt118n11.Model;

import java.util.ArrayList;

public class Request {
    private String phone;
    private String name;
    private String address;
    private String total;
    private ArrayList<Order> cartList;
    private String status;

    public Request() {
    }

    public Request(String phone, String name, String address, String total, ArrayList<Order> cartList) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.cartList = cartList;
        this.status = "0"; //0: đơn chờ, 1: đã nhận đơn và đang giao, 2: đã hoàn
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<Order> getCartList() {
        return cartList;
    }

    public void setCartList(ArrayList<Order> cartList) {
        this.cartList = cartList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
