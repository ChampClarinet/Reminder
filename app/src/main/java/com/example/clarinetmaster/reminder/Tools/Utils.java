package com.example.clarinetmaster.reminder.Tools;

import android.text.format.DateUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.text.format.DateUtils.FORMAT_NUMERIC_DATE;
import static android.text.format.DateUtils.FORMAT_SHOW_DATE;
import static android.text.format.DateUtils.FORMAT_SHOW_YEAR;
import static android.text.format.DateUtils.MINUTE_IN_MILLIS;

public class Utils {

    public static String dateLabel(Calendar dateTime){
        DateFormat format = DateFormat.getDateInstance(DateFormat.LONG);
        return format.format(dateTime.getTime());
    }

    public static long getRemainingTime(Calendar calendar){
        Calendar now = Calendar.getInstance();
        return calendar.getTimeInMillis() - now.getTimeInMillis();
    }

    public static String getRemainTimeLabelText(Calendar calendar) {
        long diff = getRemainingTime(calendar);
        Log.i("diff", ""+diff);
        long days = diff / (24 * 60 * 60 * 1000);
        if(diff < 0){
            diff = -1 * diff;
            days = -1 * days;
            if(diff < 60000) return "just passed.";
            String out = "passed ";
            if(days > 0) out += days + " day(s) ";
            diff %= (24 * 60 * 60 * 1000);
            if(diff > 3600000) out += Long.toString(diff / 3600000) + " hour(s) ";
            diff %= 3600000;
            if(diff > 60000) out += Long.toString(diff / 60000) + " minute(s) ";
            out += "ago.";
            return out;
        }
        if(diff < 60000) return "just now!!!";
        String out = "";
        if(days > 0) out += days + " day(s) ";
        diff %= (24 * 60 * 60 * 1000);
        if(diff > 3600000) out += Long.toString(diff / 3600000) + " hour(s) ";
        diff %= 3600000;
        if(diff > 60000) out += Long.toString(diff / 60000) + " minute(s) ";
        out += "left.";
        return out;
    }

}
