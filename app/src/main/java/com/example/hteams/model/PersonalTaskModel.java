package com.example.hteams.model;

public class PersonalTaskModel {
    String idTask;
    String idGroup;
    String id_table;
    String groupImage;
    String GroupName;
    String NameofTask;
    String Status;
    String DueDate;

    public PersonalTaskModel(String idTask, String idGroup, String id_table, String groupImage, String groupName, String nameofTask, String status, String dueDate) {
        this.idTask = idTask;
        this.idGroup = idGroup;
        this.id_table = id_table;
        this.groupImage = groupImage;
        GroupName = groupName;
        NameofTask = nameofTask;
        Status = status;
        DueDate = dueDate;
    }

    public String getIdTask() {
        return idTask;
    }

    public String getIdGroup() {
        return idGroup;
    }

    public String getId_table() {
        return id_table;
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
