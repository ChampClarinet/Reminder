package com.example.clarinetmaster.reminder.Tools;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTime extends Date{

    /**
     * Use this constructor if you already have Date object
     */
    public DateTime(Date date){
        super(
                date.getYear(),
                date.getMonth(),
                date.getDate(),
                date.getHours(),
                date.getMinutes()
        );
    }

    /**
     * This constructor will set time to current time
     */
    public DateTime(){}

    public DateTime(int year, int month, int date, int hrs, int min) {
        super(year-1900, month-1, date, hrs, min);
    }

    @Override
    public int getMonth() {
        return (super.getMonth()+1);
    }

    @Override
    public int getYear() {
        return (super.getYear()+1900);
    }

    @Override
    public void setMonth(int month){
        super.setMonth(month-1);
    }

    @Override
    public void setYear(int year){
        super.setYear(year-1900);
    }

    public int getBuddhistYear(){
        return getYear()+543;
    }

    public void setBuddhistYear(int buddhistYear){
        setYear(buddhistYear-543);
    }

    public long getRemainingTime(){
        Date curDate = new Date();
        long diffLong = this.getTime() - curDate.getTime();
        return TimeUnit.DAYS.convert(diffLong, TimeUnit.MILLISECONDS);
    }

}