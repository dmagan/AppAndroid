package com.example.myapplication;

import android.graphics.Bitmap;

public class Message {
    private String _id;
    private String userId;
    private String message; // متن پیام
    private Bitmap image;
    private boolean isSent;
    private String imagePath; // مسیر تصویر
    private String timestamp; // زمان ارسال پیام

    public Message(String message, boolean isSent, String timestamp) {
        this.message = message;
        this.isSent = isSent;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
