package com.vantagetechnic.wordwidget.Widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Handles refreshing the widget after a certain interval
 */
public class WidgetAlarm {
    private final static String TAG = "WidgetAlarm";
    private final static int INTERVAL = 60000;

    private int id;
    private Context context;
    private PendingIntent started;

    public WidgetAlarm(Context ctx, int wid) {
        id = wid;
        context = ctx;
    }

    public void start() {
        if(started == null) {
            int refreshInterval = WidgetViewConfigure.getInterval(context, this.id);

            Log.d(TAG, "Setting Alarm for ID: " + this.id + " With Minutes: " + refreshInterval);

            Intent alarmIntent = new Intent(context, WidgetService.class);
            alarmIntent.setAction(WidgetViewConfigure.REFRESH);
            alarmIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, this.id);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, this.id + 1000, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

            this.started = pendingIntent;

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            // RTC does not wake the device up
            alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), INTERVAL * refreshInterval, pendingIntent);
        }
    }

    public void stop() {
        if(this.started != null) {
            Log.d(TAG, "Stopping Alarm for ID: " + this.id);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(this.started);

            this.started = null;
        }
    }
}
