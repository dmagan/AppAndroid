package com.example.myapplication;

public class MessageRequest {
    private String message;
    private String imagePath;
    private boolean isSent;
    private String timeStamp;

    // Constructor, getters, and setters
    public MessageRequest(String message, String imagePath, boolean isSent, String timeStamp) {
        this.message = message;
        this.imagePath = imagePath;
        this.isSent = isSent;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
