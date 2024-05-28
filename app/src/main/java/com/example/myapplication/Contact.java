package com.example.myapplication;

public class Contact {
    private String name;
    private String status;
    private int startColor; // رنگ شروع گرادیانت
    private int endColor;   // رنگ پایان گرادیانت

    public Contact(String name, String status, int startColor, int endColor) {
        this.name = name;
        this.status = status;
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public int getStartColor() {
        return startColor;
    }

    public int getEndColor() {
        return endColor;
    }
}
