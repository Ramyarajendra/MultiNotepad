package com.example.multinotes;

import java.io.Serializable;

public class Notes implements Serializable {
    private String lastUpdated; //last updated time
    private String notesTitle; //notes title
    private String notesText; //notes text

    public Notes () {

    }
    public Notes(String lu,String nt,String nT){ //constructor to initialize
        this.lastUpdated=lu;
        this.notesTitle=nt;
        this.notesText=nT;
    }
    //getters and setters
    public String getLastUpdate() {
        return lastUpdated;
    }

    public void setLastUpdate(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getNotesTitle() {
        return notesTitle;
    }

    public void setNotesTitle(String noteTitle) {
        this.notesTitle = noteTitle;
    }

    public String getNotesText() {
        return notesText;
    }

    public void setNotesText(String noteText) {
        this.notesText = noteText;
    }
    @Override
    public String toString() { //overrides toString() method of object class
        return "Notes{" +
                "lastUpdated='" + lastUpdated + '\'' +
                ", notesTitle='" + notesTitle + '\'' +
                ", notesText='" + notesText + '\'' +
                '}';
    }
}
