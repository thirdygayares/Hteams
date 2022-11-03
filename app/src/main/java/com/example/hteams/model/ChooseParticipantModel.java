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
}
