package com.example.dto;

public class CalendarVO {
    private String date;
    private int userid;
    private String title;
    private int mood;
    private String body;

    public int getMood() {
        return mood;
    }

    public int getUserid() {
        return userid;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
