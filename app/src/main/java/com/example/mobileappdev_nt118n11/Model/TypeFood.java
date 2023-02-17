package com.example.mobileappdev_nt118n11.Model;

public class TypeFood {
    private String Id;
    private String Name;

    public TypeFood(String id, String name) {
        Id = id;
        Name = name;
    }

    public TypeFood(String name) {
        Name = name;
    }

    public TypeFood() {
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

