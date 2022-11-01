package com.example.hteams.model;

public class UpdateListModel {

    int checked;
    String taskname;

 public UpdateListModel (int checked, String taskname)   {
    this.checked = checked;
    this.taskname = taskname;
 }

    public int getChecked() {
     return checked;
    }

    public String getTaskname() {
        return taskname;
    }
}
