package com.example.hteams.model.FirebaseModel;

public class FirebaseAddTAsk {

    String ID_GROUP;
    String ID_TABLE;
    String ID_STUDENTS;
    String TASK_NAME;
    String STATUS;
    String DueDate;
    String DueTime;

    public FirebaseAddTAsk(String ID_GROUP, String ID_TABLE, String ID_STUDENTS, String TASK_NAME, String STATUS, String dueDate, String dueTime) {
        this.ID_GROUP = ID_GROUP;
        this.ID_TABLE = ID_TABLE;
        this.ID_STUDENTS = ID_STUDENTS;
        this.TASK_NAME = TASK_NAME;
        this.STATUS = STATUS;
        DueDate = dueDate;
        DueTime = dueTime;
    }


    public String getID_GROUP() {
        return ID_GROUP;
    }

    public String getID_TABLE() {
        return ID_TABLE;
    }

    public String getID_STUDENTS() {
        return ID_STUDENTS;
    }

    public String getTASK_NAME() {
        return TASK_NAME;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public String getDueDate() {
        return DueDate;
    }

    public String getDueTime() {
        return DueTime;
    }
}
