package com.example.hteams.model;

public class DisplaySiteModel {

    int siteicon;
    String customsitename;
    String siteName;


    public DisplaySiteModel(int siteicon, String customsitename){
        this.siteicon = siteicon;
        this.customsitename = customsitename;
    }

    public DisplaySiteModel(String customsitename, String siteName) {
        this.customsitename = customsitename;
        this.siteName = siteName;

    }

    public int getSiteicon() {
        return siteicon;
    }

    public String getCustomsitename() {

        return customsitename;
    }

    public String getSiteName() {
        return siteName;
    }
}
