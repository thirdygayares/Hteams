package com.example.hteams.model;

public class SQLITEPARTICIPANTMODEL {
    String ID;
    int ID_GROUP;
    Boolean ACCEPTED;

    public SQLITEPARTICIPANTMODEL(String ID) {
        this.ID = ID;
    }

    public SQLITEPARTICIPANTMODEL(String ID, int ID_GROUP) {
        this.ID = ID;
        this.ID_GROUP = ID_GROUP;
    }


    public SQLITEPARTICIPANTMODEL(String ID, int ID_GROUP, Boolean ACCEPTED) {
        this.ID = ID;
        this.ID_GROUP = ID_GROUP;
        this.ACCEPTED = ACCEPTED;
    }


    public String getID() {
        return ID;
    }

    public Boolean getACCEPTED() {
        return ACCEPTED;
    }

    public int getID_GROUP() {
        return ID_GROUP;
    }
}
