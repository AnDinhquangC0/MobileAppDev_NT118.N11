package com.example.mobileappdev_nt118n11.Model;

import java.util.ArrayList;

public class Request {
    private String phone;
    private String name;
    private String address;
    private String total;
    private ArrayList<Order> foods;
    private String status;
    private String orderDate;

    public Request() {
    }

    public Request(String phone, String name, String address, String total, ArrayList<Order> foods, String orderdate) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.foods = foods;
        this.orderDate = orderdate;
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Order> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Order> foods) {
        this.foods = foods;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
