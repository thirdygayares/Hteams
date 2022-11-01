package com.example.hteams.model;

public class FileModel {

    String filename;
    String type;

    public FileModel(String filename, String type) {
        this.filename = filename;
        this.type = type;
    }

    public FileModel(String filename) {
        this.filename = filename;
    }


    public String getFilename() {
        return filename;
    }

    public String getType() {
        return type;
    }


}
