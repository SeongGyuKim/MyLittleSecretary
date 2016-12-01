package com.example.administrator.mylittlesecretary;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

/**
 * Created by GHKwon on 2016-02-17.
 */
public class BroadcastD extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;
    String title;
    String schedule;
    int notificationSet;
    int sound;

    @Override
    public void onReceive(Context context, Intent intent) {

        title = intent.getStringExtra("title");
        if(intent.getStringExtra("place") != null) {
            schedule = title + "  장소 : " + intent.getStringExtra("place");
        }
        else {
                schedule = title;
        }


        sound = intent.getIntExtra("sound", -1);

        Log.d("schedule!", schedule);
        Log.d("sound!", Integer.toString(sound));

        switch (sound) {
            case 0 :
                notificationSet = 0;
                break;
            case 1 :
                notificationSet = Notification.DEFAULT_SOUND;
                break;
            case 2 :
                notificationSet = Notification.DEFAULT_VIBRATE;
                break;
            case 3 :
                notificationSet = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;
                break;
        }
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, CalendarActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);

        builder.setSmallIcon(R.drawable.symbol_icon).setTicker("HETT").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("일정이요!").setContentText(schedule)
                .setDefaults(notificationSet)
                .setContentIntent(pendingIntent).setAutoCancel(true);

        notificationmanager.notify(1, builder.build());
    }
}