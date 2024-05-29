package com.example.myapplication;

import android.graphics.Bitmap;

public class Message {
    private String text;
    private boolean isSent;
    private Bitmap image; // اضافه کردن فیلد image

    public Message(String text, boolean isSent) {
        this.text = text;
        this.isSent = isSent;
    }

    public String getText() {
        return text;
    }

    public boolean isSent() {
        return isSent;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
