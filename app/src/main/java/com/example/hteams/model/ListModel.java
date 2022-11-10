package com.example.hteams.model;

public class ListModel {
    String listsId;
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

    public ListModel(String listsId, String taskId, String status, String tasktitle) {
        this.listsId = listsId;
        this.taskId = taskId;
        Status = status;
        this.tasktitle = tasktitle;
    }

    public String getListsId() {
        return listsId;
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
