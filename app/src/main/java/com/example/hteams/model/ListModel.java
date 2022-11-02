package com.example.hteams.model;

public class ListModel {
    boolean Status;
    String tasktitle;

    public ListModel(boolean status, String tasktitle) {
        Status = status;
        this.tasktitle = tasktitle;
    }

    public boolean getStatus() {
        return Status;
    }

    public String getTasktitle() {
        return tasktitle;
    }

}
