package com.example.hteams.Testing;

public class SubjectModel {

    String NAME_SUBJECT;
    String TEACHER;
    String SECTION;

    public SubjectModel( String NAME_SUBJECT, String TEACHER, String SECTION) {
        this.NAME_SUBJECT = NAME_SUBJECT;
        this.TEACHER = TEACHER;
        this.SECTION = SECTION;
    }




    public String getNAME_SUBJECT() {
        return NAME_SUBJECT;
    }

    public void setNAME_SUBJECT(String NAME_SUBJECT) {
        this.NAME_SUBJECT = NAME_SUBJECT;
    }

    public String getTEACHER() {
        return TEACHER;
    }

    public void setTEACHER(String TEACHER) {
        this.TEACHER = TEACHER;
    }

    public String getSECTION() {
        return SECTION;
    }

    public void setSECTION(String SECTION) {
        this.SECTION = SECTION;
    }


}
