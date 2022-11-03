package com.example.hteams.model;

public class SubjectlistModel {

    String subjectId;
    String subjectname;

    public SubjectlistModel( String subjectname) {

        this.subjectname = subjectname;
    }

    public SubjectlistModel(String subjectId, String subjectname) {
        this.subjectId = subjectId;
        this.subjectname = subjectname;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getSubjectname() {
        return subjectname;
    }
}
