package com.example.hteams.model;

public class ListModel {
    Integer Status;
    String tasktitle;

    public ListModel(Integer status, String tasktitle) {
        Status = status;
        this.tasktitle = tasktitle;
    }

    public Integer getStatus() {
        return Status;
    }

    public String getTasktitle() {
        return tasktitle;
    }

}
