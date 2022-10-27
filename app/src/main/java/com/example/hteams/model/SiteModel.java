package com.example.hteams.model;

public class SiteModel {

    int meeticon;
    String sitename;


    public SiteModel(int meeticon, String sitename) {
        this.meeticon = meeticon;
        this.sitename = sitename;
    }

    public int getMeeticon() {
        return meeticon;
    }

    public String getSitename() {
        return sitename;
    }
}
