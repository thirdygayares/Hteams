package com.example.hteams.model;

import java.util.List;

public class FirebaseCreateGroup {
    
    String GroupName;
    String Course;
    String Professor;
    String Description;
    private List<String> participants;

    public FirebaseCreateGroup(String groupName, String course, String professor, String description) {
        GroupName = groupName;
        Course = course;
        Professor = professor;
        Description = description;
    }


    public FirebaseCreateGroup(String groupName, String course, String professor, String description, List<String> participants) {
        GroupName = groupName;
        Course = course;
        Professor = professor;
        Description = description;
        this.participants = participants;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public String getGroupName() {
        return GroupName;
    }

    public String getCourse() {
        return Course;
    }

    public String getProfessor() {
        return Professor;
    }

    public String getDescription() {
        return Description;
    }

}
