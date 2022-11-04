package com.example.hteams.model;

public class AssigneeModel {
    int image;
    String Name;
    String STUDENT_ID;
    String imgsrc;

    public AssigneeModel(int image, String name) {
        this.image = image;
        Name = name;
    }

    public AssigneeModel(int image, String name, String STUDENT_ID) {
        this.image = image;
        Name = name;
        this.STUDENT_ID = STUDENT_ID;
    }


    public AssigneeModel(String name, String STUDENT_ID, String imgsrc) {
        Name = name;
        this.STUDENT_ID = STUDENT_ID;
        this.imgsrc = imgsrc;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return Name;
    }
}
