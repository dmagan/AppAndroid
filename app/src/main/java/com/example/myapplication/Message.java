package com.example.myapplication;

import android.graphics.Bitmap;

public class Message {
    private String text;
    private Bitmap image;
    private boolean isSent;
    private String imagePath; // مسیر تصویر
    private String timeStamp; // زمان ارسال پیام

    public Message(String text, boolean isSent, String timeStamp) {
        this.text = text;
        this.isSent = isSent;
        this.timeStamp = timeStamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
