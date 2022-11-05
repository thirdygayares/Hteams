package com.example.hteams.model;

public class GroupPageModel {

    int TaskId;
    int GroupId;
    int TableId;
    String NameofTask;
    String Status;
    String DueDate;
    int participant_photo;
    String participant_src_photo;
    int Position;


    public GroupPageModel(String nameofTask, String status, String dueDate) {
        NameofTask = nameofTask;
        Status = status;
        DueDate = dueDate;
    }

    public GroupPageModel(String nameofTask, String status, String dueDate, int participant_photo) {
        NameofTask = nameofTask;
        Status = status;
        DueDate = dueDate;
        this.participant_photo = participant_photo;
    }

    public GroupPageModel(String nameofTask, String status, String dueDate, String participant_src_photo) {
        NameofTask = nameofTask;
        Status = status;
        DueDate = dueDate;
        this.participant_src_photo = participant_src_photo;
    }

    public GroupPageModel(int taskId, int groupId, int tableId, String nameofTask, String status, String dueDate, String participant_src_photo, int position) {
        TaskId = taskId;
        GroupId = groupId;
        TableId = tableId;
        NameofTask = nameofTask;
        Status = status;
        DueDate = dueDate;
        this.participant_src_photo = participant_src_photo;
        Position = position;
    }

    public int getTaskId() {
        return TaskId;
    }

    public int getGroupId() {
        return GroupId;
    }

    public int getTableId() {
        return TableId;
    }

    public int getPosition() {
        return Position;
    }

    public String getParticipant_src_photo() {
        return participant_src_photo;
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
