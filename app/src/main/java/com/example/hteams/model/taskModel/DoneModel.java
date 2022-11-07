package com.example.hteams.model.taskModel;

public class DoneModel {

    int TaskId;
    int GroupId;
    int TableId;
    String NameofTask;
    String Status;
    String DueDate;
    String participant_src_photo;


    public DoneModel(int taskId, int groupId, int tableId, String nameofTask, String status, String dueDate, String participant_src_photo) {
        TaskId = taskId;
        GroupId = groupId;
        TableId = tableId;
        NameofTask = nameofTask;
        Status = status;
        DueDate = dueDate;
        this.participant_src_photo = participant_src_photo;
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

    public String getNameofTask() {
        return NameofTask;
    }

    public String getStatus() {
        return Status;
    }

    public String getDueDate() {
        return DueDate;
    }

    public String getParticipant_src_photo() {
        return participant_src_photo;
    }
}
