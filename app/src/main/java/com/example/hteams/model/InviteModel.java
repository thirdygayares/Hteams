package com.example.hteams.model;

public class InviteModel {
    String groupId;
    String image;
    String name;
   String studentid;
   boolean accepted;
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
        this.studentid = id;
    }

    public InviteModel(String groupId, String id,boolean accepted) {
        this.groupId = groupId;
        this.studentid = id;
        this.accepted = accepted;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getId() {
        return studentid;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }




}
