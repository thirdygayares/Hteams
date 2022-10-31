package com.example.hteams.model;

public class ListModel {
    int Status;
    String tasktitle;

    public ListModel(int status, String tasktitle) {
        Status = status;
        this.tasktitle = tasktitle;
    }

    public int getStatus() {
        return Status;
    }

    public String getTasktitle() {
        return tasktitle;
    }

}
