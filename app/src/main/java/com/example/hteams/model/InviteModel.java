package com.example.hteams.model;

public class InviteModel {
    String image;
    String name;
   String id;
    public InviteModel(String name) {
        this.name = name;
    }

    public InviteModel(String image, String name) {
        this.image = image;
        this.name = name;
    }


    public InviteModel(String image, String name, String id) {
        this.image = image;
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }




}
