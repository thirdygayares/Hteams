package com.example.hteams.model;

public class PersonalTaskModel {
    String groupImage;
    String GroupName;
    String NameofTask;
    String Status;
    String DueDate;

    public PersonalTaskModel(String groupImage, String groupName, String nameofTask, String status, String dueDate) {
        this.groupImage = groupImage;
        GroupName = groupName;
        NameofTask = nameofTask;
        Status = status;
        DueDate = dueDate;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public String getGroupName() {
        return GroupName;
    }

    public String getNameofTask() {
        return NameofTask;
    }

    public String getStatus() {
        return Status;
    }

    public String getDueDate() {
        return DueDate;
    }
}
