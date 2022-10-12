package com.example.hteams.model;

public class FireBaseParticipant {

    String Participant;
    String testing;

    public FireBaseParticipant(String participant) {
        Participant = participant;
    }

    public FireBaseParticipant(String participant, String testing) {
        Participant = participant;
        this.testing = testing;
    }

    public String getTesting() {
        return testing;
    }

    public String getParticipant() {
        return Participant;
    }

}
