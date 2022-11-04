package com.example.hteams.model;

public class GroupModel {
    String GROUPID;
    String groupImage;
    String groupTitle;
    String shortDescription;
    String professor;
    String subject;

    public GroupModel(String GROUPID, String groupImage, String groupTitle, String shortDescription, String professor, String subject) {
        this.GROUPID = GROUPID;
        this.groupImage = groupImage;
        this.groupTitle = groupTitle;
        this.shortDescription = shortDescription;
        this.professor = professor;
        this.subject = subject;
    }

    public String getGROUPID() {
        return GROUPID;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getProfessor() {
        return professor;
    }

    public String getSubject() {
        return subject;
    }
}
