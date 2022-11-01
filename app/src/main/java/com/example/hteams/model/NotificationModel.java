package com.example.hteams.model;

public class NotificationModel {
    int notificationPhoto;
    String content;
    String date;
    String time;
    String type;
    Boolean Status; //read or unread

    public NotificationModel(int notificationPhoto, String content, String date, String time, String type, Boolean status) {
        this.notificationPhoto = notificationPhoto;
        this.content = content;
        this.date = date;
        this.time = time;
        this.type = type;
        Status = status;
    }

    public int getNotificationPhoto() {
        return notificationPhoto;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public Boolean getStatus() {
        return Status;
    }
}
