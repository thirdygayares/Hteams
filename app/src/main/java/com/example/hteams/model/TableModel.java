package com.example.hteams.model;

public class TableModel {
    String groupId;
    String tableName;

    public TableModel(String groupId, String tableName) {
        this.groupId = groupId;
        this.tableName = tableName;
    }


    public String getGroupId() {
        return groupId;
    }

    public String getTableName() {
        return tableName;
    }
}
