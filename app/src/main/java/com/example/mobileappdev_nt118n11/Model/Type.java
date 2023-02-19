package com.example.mobileappdev_nt118n11.Model;

public class Type {
    private String Id;
    private String Name;

    public Type(String id, String name) {
        Id = id;
        Name = name;
    }

    public Type() {
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
}

