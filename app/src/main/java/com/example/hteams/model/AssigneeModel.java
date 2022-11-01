package com.example.hteams.model;

public class AssigneeModel {
    int image;
    String Name;

    public AssigneeModel(int image, String name) {
        this.image = image;
        Name = name;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return Name;
    }
}
