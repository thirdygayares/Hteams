package com.example.hteams.model;

public class DisplaySiteModel {

    int siteicon;
    String customsitename;

    public DisplaySiteModel(int siteicon, String customsitename){
        this.siteicon = siteicon;
        this.customsitename = customsitename;

    }

    public int getSiteicon() {

        return siteicon;
    }

    public String getCustomsitename() {

        return customsitename;
    }
}
