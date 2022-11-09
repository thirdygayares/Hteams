package com.example.hteams.model;

public class DisplaySiteModel {

    int siteicon;
    String customsitename;
    String siteName;
    String link;


    public DisplaySiteModel(int siteicon, String customsitename){
        this.siteicon = siteicon;
        this.customsitename = customsitename;
    }

    public DisplaySiteModel(String customsitename, String siteName, String link) {
        this.customsitename = customsitename;
        this.siteName = siteName;
        this.link = link;

    }

    public String getLink() {
        return link;
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
