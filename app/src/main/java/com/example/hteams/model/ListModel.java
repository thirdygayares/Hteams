package com.example.hteams.model;

public class ListModel {
    String taskId;
    String Status;
    String tasktitle;

    public ListModel(String status, String tasktitle) {
        Status = status;
        this.tasktitle = tasktitle;
    }

    public ListModel(String taskId, String status, String tasktitle) {
        this.taskId = taskId;
        Status = status;
        this.tasktitle = tasktitle;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getStatus() {
        return Status;
    }

    public String getTasktitle() {
        return tasktitle;
    }

}
