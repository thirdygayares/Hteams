package com.example.hteams.model;

public class ChooseParticipantModel {
    String ID;
    String NAME;
    String IMAGE;

    public ChooseParticipantModel(String ID, String NAME, String IMAGE) {
        this.ID = ID;
        this.NAME = NAME;
        this.IMAGE = IMAGE;
    }



    public String getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }

    public String getIMAGE() {
        return IMAGE;
    }


    //for sqlite purposes and firebase
    //gamitin natong class para di sayang yung MOdel

    int ID_GROUP;
    Boolean ACCEPTED;

    public ChooseParticipantModel(String ID, int ID_GROUP) {
        this.ID = ID;
        this.ID_GROUP = ID_GROUP;

    }

    public ChooseParticipantModel(String ID, int ID_GROUP, Boolean ACCEPTED) {
        this.ID = ID;
        this.ID_GROUP = ID_GROUP;
        this.ACCEPTED = ACCEPTED;
    }

    public Boolean getACCEPTED() {
        return ACCEPTED;
    }

    public int getID_GROUP() {
        return ID_GROUP;
    }



}
