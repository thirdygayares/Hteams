package com.example.hteams.model;

public class GroupPageParentModel {
    String TableId;
    String TableName;
    private boolean isExpandable;
    int Position;

    public GroupPageParentModel(String tableId, String tableName) {
        TableId = tableId;
        TableName = tableName;
        isExpandable = true;
    }

    public GroupPageParentModel(String tableId, String tableName, int position) {
        TableId = tableId;
        TableName = tableName;
        Position = position;
        isExpandable = true;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public int getPosition() {
        return Position;
    }

    public GroupPageParentModel(boolean isExpandable) {
        this.isExpandable = isExpandable;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public String getTableId() {
        return TableId;
    }

    public String getTableName() {
        return TableName;
    }

}
