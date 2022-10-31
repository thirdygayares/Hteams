package com.example.hteams.model;

public class ViewTaskModel {

    int profilepicture;
    String participantName;
    String postdate;
    int viewcount;
    int commentview;
    String description;
    int filescount;
    int likecount;
    int dislikecount;
    int save;

    public ViewTaskModel(int profilepicture, String participantName, String postdate, int viewcount, int commentview, String description, int filescount, int likecount, int dislikecount) {
        this.profilepicture = profilepicture;
        this.participantName = participantName;
        this.postdate = postdate;
        this.viewcount = viewcount;
        this.commentview = commentview;
        this.description = description;
        this.filescount = filescount;
        this.likecount = likecount;
        this.dislikecount = dislikecount;
    }

    public int getProfilepicture() {
        return profilepicture;
    }

    public String getParticipantName() {
        return participantName;
    }

    public String getPostdate() {
        return postdate;
    }

    public int getViewcount() {
        return viewcount;
    }

    public int getCommentview() {
        return commentview;
    }

    public String getDescription() {
        return description;
    }

    public int getFilescount() {
        return filescount;
    }

    public int getLikecount() {
        return likecount;
    }

    public int getDislikecount() {
        return dislikecount;
    }

    public int getSave() {
        return save;
    }
}
