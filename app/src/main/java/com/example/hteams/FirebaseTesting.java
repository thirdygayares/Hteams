package com.example.hteams;

public class FirebaseTesting {
    String College;
    String Email;
    String ContactNumber;
    String Course;
    String Section;
    String Name;

    public FirebaseTesting(String college, String email, String contactNumber, String course, String section, String name) {
        College = college;
        Email = email;
        ContactNumber = contactNumber;
        Course = course;
        Section = section;
        Name = name;
    }

    public String getCollege() {
        return College;
    }

    public String getEmail() {
        return Email;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public String getCourse() {
        return Course;
    }

    public String getSection() {
        return Section;
    }

    public String getName() {
        return Name;
    }
}

