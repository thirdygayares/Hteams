package com.example.hteams.Testing;

import java.util.ArrayList;

public class Testing1Model {
    String ID_STUDENTS;
    byte[] STUDENTS_IMAGE;
    String STUDENTS_IMAGE_STRING;
    String NAME;
    String EMAIL;
    String SECTION;
    String COURSE;
    String COLLEGE;


    public Testing1Model(String ID_STUDENTS, byte[] STUDENTS_IMAGE, String NAME, String EMAIL, String SECTION, String COURSE, String COLLEGE) {
        this.ID_STUDENTS = ID_STUDENTS;
        this.STUDENTS_IMAGE = STUDENTS_IMAGE;
        this.NAME = NAME;
        this.EMAIL = EMAIL;
        this.SECTION = SECTION;
        this.COURSE = COURSE;
        this.COLLEGE = COLLEGE;
    }


    public Testing1Model(String ID_STUDENTS, String STUDENTS_IMAGE_STRING, String NAME, String EMAIL, String SECTION, String COURSE, String COLLEGE) {
        this.ID_STUDENTS = ID_STUDENTS;
        this.STUDENTS_IMAGE_STRING = STUDENTS_IMAGE_STRING;
        this.NAME = NAME;
        this.EMAIL = EMAIL;
        this.SECTION = SECTION;
        this.COURSE = COURSE;
        this.COLLEGE = COLLEGE;
    }

    public String getSTUDENTS_IMAGE_STRING() {
        return STUDENTS_IMAGE_STRING;
    }

    public byte[] getSTUDENTS_IMAGE() {
        return STUDENTS_IMAGE;
    }

    public String getID_STUDENTS() {
        return ID_STUDENTS;
    }

    public String getNAME() {
        return NAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getSECTION() {
        return SECTION;
    }

    public String getCOURSE() {
        return COURSE;
    }

    public String getCOLLEGE() {
        return COLLEGE;
    }
}
