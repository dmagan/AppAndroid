package com.example.myapplication;

public class Contact {
    private String name;
    private String status;
    private int imageResource;

    public Contact(String name, String status, int imageResource) {
        this.name = name;
        this.status = status;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public int getImageResource() {
        return imageResource;
    }
}
