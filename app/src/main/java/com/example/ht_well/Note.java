package com.example.ht_well;

// Simple class, defining the parameters of a note/entry in the app.

public class Note {
    private long ID;
    private String title;
    private String content;
    private String date;
    private String time;

    //parameters for the note/entry
    Note(String title, String content, String date, String time) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
    }
    //Parameter that are needed for notes to be stored in a database.
    //Helps organising information
    Note (long id, String title, String content, String date, String time) {
        this.ID = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
    }
    // empty constructor. A constructor is needed, and information is inserted later.
    Note(){

    }
    // Methods grant access into information.
    // returns values, which are then inserted into the object.
    public long getID() {
        return ID;
    }
    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
