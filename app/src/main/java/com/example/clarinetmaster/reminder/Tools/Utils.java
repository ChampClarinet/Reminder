package com.example.clarinetmaster.reminder.Tools;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static String dateLabel(Calendar dateTime){
        DateFormat format = DateFormat.getDateInstance(DateFormat.LONG);
        return format.format(dateTime.getTime());
    }

    public static long getRemainingTime(Calendar date){
        Calendar curDate = Calendar.getInstance();
        long diffLong = date.getTimeInMillis() - curDate.getTimeInMillis();
        return TimeUnit.DAYS.convert(diffLong, TimeUnit.MILLISECONDS);
    }

}
