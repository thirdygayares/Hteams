package com.example.hteams.model;

public class InviteModel {
    String image;
    String name;

    public InviteModel(String name) {
        this.name = name;
    }

    public InviteModel(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
