package com.example.clarinetmaster.reminder.Tools;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.clarinetmaster.reminder.Models.Event;

import java.text.DateFormat;
import java.util.Calendar;

public class Utils {

    public static String dateLabel(Calendar dateTime){
        DateFormat format = DateFormat.getDateInstance(DateFormat.LONG);
        return format.format(dateTime.getTime());
    }

    public static long getRemainingTimeLong(Calendar calendar){
        Calendar now = Calendar.getInstance();
        return calendar.getTimeInMillis() - now.getTimeInMillis();
    }

    public static String getRemainTimeLabelText(Calendar calendar) {
        long diff = getRemainingTimeLong(calendar);
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

    public static int createNotification(Context context, Event event){

        Calendar triggerTime = event.getDate();

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        Intent serviceIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");

        serviceIntent.putExtra("event", event);

        int notificationID = event.getId();
        int cancel = PendingIntent.FLAG_CANCEL_CURRENT;

        PendingIntent broadcast = PendingIntent.getBroadcast(context, notificationID, serviceIntent, cancel);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime.getTimeInMillis(), broadcast);

        Log.i("notificationCreator", "Notification created at "+triggerTime.getTime());

        return notificationID;

    }

    public static void cancelNotification(Context context, int eventID){

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent cancelServiceIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        PendingIntent cancelServicePendingIntent = PendingIntent.getBroadcast(
                context,
                eventID,
                cancelServiceIntent,
                0
        );
        alarmManager.cancel(cancelServicePendingIntent);
        Log.i("NotiCancellation", "Notification canceled");

    }

}
