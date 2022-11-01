package com.example.hteams.model;

public class ListDisplayModel {

    boolean checked;
    String taskname;

    public ListDisplayModel (boolean checked, String taskname)   {
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
