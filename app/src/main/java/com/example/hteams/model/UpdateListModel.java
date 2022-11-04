package com.example.hteams.model;

public class UpdateListModel {

    boolean checked;
    String taskname;

 public UpdateListModel (boolean checked, String taskname)   {
    this.checked = checked;
    this.taskname = taskname;
 }

    public boolean getChecked() {
     return checked;
    }

    public String getTaskname() {
        return taskname;
    }
}
