package com.example.taskque_mobile_app;

public class Links {
    private int linksID;
    private int tasksID;
    private String title;
    private String link;

    public Links(int linksID, int tasksID, String title, String link) {
        this.linksID = linksID;
        this.tasksID = tasksID;
        this.title = title;
        this.link = link;
    }

    public int getLinksID() {
        return linksID;
    }

    public void setLinksID(int linksID) {
        this.linksID = linksID;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
