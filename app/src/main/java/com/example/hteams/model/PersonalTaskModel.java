package com.example.hteams.model;

public class PersonalTaskModel {
    int groupImage;
    String GroupName;
    String NameofTask;
    String Status;
    String DueDate;

    public PersonalTaskModel(int groupImage, String groupName, String nameofTask, String status, String dueDate) {
        this.groupImage = groupImage;
        GroupName = groupName;
        NameofTask = nameofTask;
        Status = status;
        DueDate = dueDate;
    }

    public int getGroupImage() {
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
