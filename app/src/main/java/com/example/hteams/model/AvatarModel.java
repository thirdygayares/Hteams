package com.example.hteams.model;

public class AvatarModel {
    String avatarName;
    int ImageSource;

    public AvatarModel(String avatarName, int imageSource) {
        this.avatarName = avatarName;
        ImageSource = imageSource;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public int getImageSource() {
        return ImageSource;
    }
}
