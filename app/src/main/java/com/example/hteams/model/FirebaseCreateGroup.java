package com.example.hteams.model;

public class FirebaseCreateGroup {
    
    String GroupName;
    String Course;
    String Professor;
    String Description;

    public FirebaseCreateGroup(String groupName, String course, String professor, String description) {
        GroupName = groupName;
        Course = course;
        Professor = professor;
        Description = description;
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
