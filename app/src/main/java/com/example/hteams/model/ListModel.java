package com.example.hteams.model;

public class ListModel {
    Integer taskId;
    Integer Status;
    String tasktitle;

    public ListModel(Integer status, String tasktitle) {
        Status = status;
        this.tasktitle = tasktitle;
    }

    public ListModel(Integer taskId, Integer status, String tasktitle) {
        this.taskId = taskId;
        Status = status;
        this.tasktitle = tasktitle;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public Integer getStatus() {
        return Status;
    }

    public String getTasktitle() {
        return tasktitle;
    }

}
