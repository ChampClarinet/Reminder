package com.example.clarinetmaster.reminder.Models;

import com.example.clarinetmaster.reminder.Tools.DateTime;

import java.util.Date;

/**
 * Created by computer on 20/11/2559.
 */

public class Event {
    private int id;
    private String title;
    private String detial;
    private DateTime date;

    public Event(String title, String detial, DateTime date) {
        this.title = title;
        this.detial = detial;
        this.date = date;
    }

    public Event(int id, String title, String detial, DateTime date) {
        this.id = id;
        this.title = title;
        this.detial = detial;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDetial() {
        return detial;
    }

    public DateTime getDate() {
        return date;
    }

}
