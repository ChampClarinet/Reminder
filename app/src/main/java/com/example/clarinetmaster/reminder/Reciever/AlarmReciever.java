package com.example.clarinetmaster.reminder.Reciever;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.example.clarinetmaster.reminder.EventDescriptionActivity;
import com.example.clarinetmaster.reminder.Models.Event;
import com.example.clarinetmaster.reminder.R;
import com.example.clarinetmaster.reminder.Tools.EventList;

public class AlarmReciever extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        Event curItem = (Event) intent.getExtras().getSerializable("event");
        EventList menu = EventList.getInstance(context);

        Intent notificationIntent = new Intent(context, EventDescriptionActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(EventDescriptionActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder.setContentTitle(curItem.getTitle())
                .setContentText("time for "+curItem.getTitle())
                .setTicker(context.getString(R.string.event_coming_up))
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        manager.notify(0, notification);

    }

}
