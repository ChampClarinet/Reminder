package com.example.clarinetmaster.reminder.Models;

import java.util.Calendar;

/**
 * Created by computer on 20/11/2559.
 */

public class Event {
    private int id;
    private String title;
    private String detial;
    private Calendar date;

    public Event(String title, String detial, Calendar date) {
        this.title = title;
        this.detial = detial;
        this.date = date;
    }

    public Event(int id, String title, String detial, Calendar date) {
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

    public Calendar getDate() {
        return date;
    }

}
