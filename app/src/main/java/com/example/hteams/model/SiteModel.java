package com.example.hteams.model;

public class SiteModel {

    int siteicon;
    String sitename;


    public SiteModel(int siteicon, String sitename) {
        this.siteicon = siteicon;
        this.sitename = sitename;
    }

    public int getSiteicon() {
        return siteicon;
    }

    public String getSitename() {
        return sitename;
    }


}
