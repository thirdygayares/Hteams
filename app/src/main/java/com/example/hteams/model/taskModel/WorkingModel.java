package com.example.hteams.model.taskModel;

public class WorkingModel {
    String TaskId;
    String GroupId;
    String TableId;
    String NameofTask;
    String Status;
    String DueDate;
    String participant_src_photo;


    public WorkingModel(String taskId, String tableId, String nameofTask, String dueDate,String participant_src_photo) {
        TaskId = taskId;
        TableId = tableId;
        NameofTask = nameofTask;
        DueDate = dueDate;
        this.participant_src_photo = participant_src_photo;

    }

    public WorkingModel(String participant_src_photo){
        this.participant_src_photo = participant_src_photo;

    }



    public String getTaskId() {
        return TaskId;
    }

    public String getGroupId() {
        return GroupId;
    }

    public String getTableId() {
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
