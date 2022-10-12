package com.example.hteams.model;

public class GroupModel {
    String groupTitle;
    String shortDescription;
    String professor;
    String subject;

    public GroupModel(String groupTitle, String shortDescription, String professor, String subject) {
        this.groupTitle = groupTitle;
        this.shortDescription = shortDescription;
        this.professor = professor;
        this.subject = subject;
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
