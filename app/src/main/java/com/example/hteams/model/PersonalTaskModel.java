package com.example.hteams.model;

public class PersonalTaskModel {
    int idTask;
    int idGroup;
    int id_table;
    String groupImage;
    String GroupName;
    String NameofTask;
    String Status;
    String DueDate;

    public PersonalTaskModel(int idTask, int idGroup, int id_table, String groupImage, String groupName, String nameofTask, String status, String dueDate) {
        this.idTask = idTask;
        this.idGroup = idGroup;
        this.id_table = id_table;
        this.groupImage = groupImage;
        GroupName = groupName;
        NameofTask = nameofTask;
        Status = status;
        DueDate = dueDate;
    }

    public int getIdTask() {
        return idTask;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public int getId_table() {
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
