package com.example.hteams.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireBaseParticipant {

    String Participant;
    String testing;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;

    public FireBaseParticipant(String participant) {
        Participant = participant;
    }

    public FireBaseParticipant(String participant, String testing) {
        Participant = participant;
        this.testing = testing;
    }

    public void setParticipant(String participant) {
        Participant = participant;
    }

    public String getTesting() {
        return testing;
    }

    public String getParticipant() {
        return Participant;
    }

}
