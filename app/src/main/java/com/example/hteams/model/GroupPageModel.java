package com.example.hteams.model;

public class GroupPageModel {
    String NameofTask;
    String Status;
    String DueDate;

    public GroupPageModel(String nameofTask, String status, String dueDate) {
        NameofTask = nameofTask;
        Status = status;
        DueDate = dueDate;
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
