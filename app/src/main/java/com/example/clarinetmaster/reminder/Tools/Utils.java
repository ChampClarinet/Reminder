package com.example.clarinetmaster.reminder.Tools;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.clarinetmaster.reminder.Models.Event;
import com.example.clarinetmaster.reminder.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    private static final String THAI = "th";
    private static final String JAPANESE = "ja";
    private static final String ENGLISH = "en";

    public static String dateLabel(Context context, Calendar dateTime){

        String systemLanguage = Locale.getDefault().getLanguage();
        String date;

        if(systemLanguage.equals(JAPANESE)){
            date = dateTime.get(Calendar.YEAR) +
                    context.getString(R.string.year) +
                    dateTime.get(Calendar.MONTH) +
                    context.getString(R.string.month) +
                    dateTime.get(Calendar.DAY_OF_MONTH) +
                    context.getString(R.string.days);
        }else if(systemLanguage.equals(THAI)){
            date = dateTime.get(Calendar.DAY_OF_MONTH) + " " +
                    dateTime.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " +
                    dateTime.get(Calendar.YEAR);
        }else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            date = dateFormat.format(new Date(dateTime.getTimeInMillis()));
        }

        return date;
    }

    public static String timeLabel(Context context, Calendar dateTime){

        String systemLanguage = Locale.getDefault().getLanguage();

        String time = Integer.toString(dateTime.get(Calendar.HOUR_OF_DAY));
        if(systemLanguage.equals(JAPANESE)){
            time += context.getString(R.string.japanese_toki) +
                    dateTime.get(Calendar.MINUTE) +
                    context.getString(R.string.minutes);
        }else if(systemLanguage.equals(THAI)){
            time += " " + context.getString(R.string.thai_o_clock) +
                    " " + dateTime.get(Calendar.MINUTE) +
                    " " + context.getString(R.string.minutes);
        }else time += ":" + dateTime.get(Calendar.MINUTE);
        return time;
    }

    public static long getRemainingTimeLong(Calendar calendar){
        Calendar now = Calendar.getInstance();
        return calendar.getTimeInMillis() - now.getTimeInMillis();
    }

    public static String getRemainTimeLabelText(Context context, Calendar calendar) {

        String systemLanguage = Locale.getDefault().getLanguage();

        Log.i("TimeLabelText", "Language Detected : " + systemLanguage);

        long diff = getRemainingTimeLong(calendar);
        long days = diff / (24 * 60 * 60 * 1000);
        if(diff < 0){
            diff = -1 * diff;
            days = -1 * days;
            if(diff < 60000) return context.getString(R.string.just_passed);
            String out = context.getString(R.string.prefix_passed);
            if(!systemLanguage.equals(JAPANESE)) out += " ";
            if(days > 0) out += days + " " + context.getString(R.string.days);
            diff %= (24 * 60 * 60 * 1000);
            if(diff > 3600000) out += " " + Long.toString(diff / 3600000) + " " + context.getString(R.string.hours);
            diff %= 3600000;
            if(diff > 60000) out += " " + Long.toString(diff / 60000) + " " + context.getString(R.string.minutes);
            if(systemLanguage.equals(THAI)) return out;
            if(systemLanguage.equals(ENGLISH)) out += " ";
            out += context.getString(R.string.ago);
            return out;
        }
        if(diff < 60000) return context.getString(R.string.now);
        String out = "";
        if(days > 0) out += days + " " + context.getString(R.string.days) + " ";
        diff %= (24 * 60 * 60 * 1000);
        if(diff > 3600000) out += Long.toString(diff / 3600000) + " " + context.getString(R.string.hours) + " ";
        diff %= 3600000;
        if(diff > 60000) out += Long.toString(diff / 60000) + " " + context.getString(R.string.minutes);
        if(!systemLanguage.equals(JAPANESE)) out += " ";
        out += context.getString(R.string.left);
        return out;

    }

    public static int createNotification(Context context, Event event){

        int minutesBeforeEvent = 30;

        long triggerTimeLong = event.getDate().getTimeInMillis() - (minutesBeforeEvent * 60000);

        Calendar triggerTime = Calendar.getInstance();
        triggerTime.setTimeInMillis(triggerTimeLong);

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
