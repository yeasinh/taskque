package com.example.taskque_mobile_app;

public class Timers {
    private int timersID;
    private int taskID;
    private int year;
    private int month;
    private int dayOFMonth;
    private int hourOFDay;
    private int minute;
    private String type;

    public Timers() {
    }

    public Timers(int timersID, int taskID, int year, int month, int dayOFMonth, int hourOFDay, int minute, String type) {
        this.timersID = timersID;
        this.taskID = taskID;
        this.year = year;
        this.month = month;
        this.dayOFMonth = dayOFMonth;
        this.hourOFDay = hourOFDay;
        this.minute = minute;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTimersID() {
        return timersID;
    }

    public void setTimersID(int timersID) {
        this.timersID = timersID;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayOFMonth() {
        return dayOFMonth;
    }

    public void setDayOFMonth(int dayOFMonth) {
        this.dayOFMonth = dayOFMonth;
    }

    public int getHourOFDay() {
        return hourOFDay;
    }

    public void setHourOFDay(int hourOFDay) {
        this.hourOFDay = hourOFDay;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
