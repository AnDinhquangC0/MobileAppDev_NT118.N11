package com.example.mobileappdev_nt118n11.Model;

public class TypeFood {
    private String Name, Id;

    public TypeFood() {}

    public TypeFood(String name) {
        Name = name;
    }

    public void setId(String code) {
        Id = code;
    }

    public String getId() {
        return Id;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }
}
