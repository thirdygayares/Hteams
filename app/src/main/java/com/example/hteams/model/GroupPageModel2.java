package com.example.hteams.model;

public class GroupPageModel2 {
    String NameofTask;
    String Status;
    String DueDate;
    int participant_photo;

    public GroupPageModel2(String nameofTask, String status, String dueDate) {
        NameofTask = nameofTask;
        Status = status;
        DueDate = dueDate;
    }

    public GroupPageModel2(String nameofTask, String status, String dueDate, int participant_photo) {
        NameofTask = nameofTask;
        Status = status;
        DueDate = dueDate;
        this.participant_photo = participant_photo;
    }

    public int getParticipant_photo() {
        return participant_photo;
    }

    public String getNameofTask() {
        return NameofTask;
    }

    public String getStatus() {
        return Status;
    }

    public String getDueDate() {
        return DueDate;
    }
}
