package com.example.clarinetmaster.reminder.Models;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by computer on 20/11/2559.
 */

public class Event implements Serializable{
    private int id;
    private String title;
    private String detail;
    private Calendar date;

    public Event(String title, String detail, Calendar date) {
        this.title = title;
        this.detail = detail;
        this.date = date;
    }

    public Event(int id, String title, String detail, Calendar date) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public Calendar getDate() {
        return date;
    }

}
