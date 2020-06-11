package com.example.jacobsthierryandroidproject.Pojo;

import java.io.Serializable;

public class Ingredient implements Serializable {

    protected int id;
    protected String name;
    protected String originalString;

    public Ingredient(int id, String name, String originalString) {
        this.id = id;
        this.name = name;
        this.originalString = originalString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(String originalString) {
        this.originalString = originalString;
    }
}
