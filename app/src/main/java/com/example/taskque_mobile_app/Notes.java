package com.example.taskque_mobile_app;

public class Notes {
    private int notesID;
    private int tasksID;
    private String title;
    private String description;

    public Notes(int notesID, int tasksID, String title, String description) {
        this.notesID = notesID;
        this.tasksID = tasksID;
        this.title = title;
        this.description = description;
    }

    public int getNotesID() {
        return notesID;
    }

    public void setNotesID(int notesID) {
        this.notesID = notesID;
    }

    public int getTasksID() {
        return tasksID;
    }

    public void setTasksID(int tasksID) {
        this.tasksID = tasksID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
