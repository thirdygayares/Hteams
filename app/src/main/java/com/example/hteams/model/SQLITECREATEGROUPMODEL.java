package com.example.hteams.model;

public class SQLITECREATEGROUPMODEL {
    String GROUPPHOTO;
    String GROUPNAME;
    String SUBJECT;
    String DESCRIPTION;
    String PROFESSORS;
    String LEADER_ID;
    String CREATOR;

    public SQLITECREATEGROUPMODEL(String GROUPPHOTO, String GROUPNAME, String SUBJECT, String DESCRIPTION, String PROFESSORS, String LEADER_ID, String CREATOR) {
        this.GROUPPHOTO = GROUPPHOTO;
        this.GROUPNAME = GROUPNAME;
        this.SUBJECT = SUBJECT;
        this.DESCRIPTION = DESCRIPTION;
        this.PROFESSORS = PROFESSORS;
        this.LEADER_ID = LEADER_ID;
        this.CREATOR = CREATOR;
    }

    public String getGROUPPHOTO() {
        return GROUPPHOTO;
    }

    public String getGROUPNAME() {
        return GROUPNAME;
    }

    public String getSUBJECT() {
        return SUBJECT;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public String getPROFESSORS() {
        return PROFESSORS;
    }

    public String getLEADER_ID() {
        return LEADER_ID;
    }

    public String getCREATOR() {
        return CREATOR;
    }

}
