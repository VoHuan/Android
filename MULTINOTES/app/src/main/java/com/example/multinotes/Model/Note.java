package com.example.multinotes.Model;

//import java.sql.Date;
import java.io.Serializable;
import  java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

public class Note implements Serializable {
    public int id;
    public String title;
    public Timestamp time;
    public String content;
    public int hour;
    public int minute;
    public byte[] image;
    public int status;

    public Note() {
    }

    public Note(int id, String title, Timestamp time, String content, int hour, int minute, byte[] image, int status) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.content = content;
        this.hour = hour;
        this.minute = minute;
        this.image = image;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
