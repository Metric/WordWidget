package com.vantagetechnic.wordwidget.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.widget.RemoteViews;

import com.vantagetechnic.wordwidget.R;

import java.util.HashMap;

public class WidgetService extends IntentService {
    static HashMap<Integer, WidgetAlarm> alarms = new HashMap<Integer, WidgetAlarm>();

    public WidgetService() {
        super("WordWidgetService");
    }

    public static void createAlarm(Context context, int id) {
        if(alarms == null) {
            alarms = new HashMap<Integer, WidgetAlarm>();
        }

        if(!alarms.containsKey(id)) {
            WidgetAlarm alarm = new WidgetAlarm(context, id);
            alarms.put(id, alarm);

            alarm.start();
        }
    }

    public static void clearAlarms() {
        for (WidgetAlarm a : alarms.values()) {
            a.stop();
        }

        alarms.clear();
    }

    public static void destroyAlarm(int id) {
        if(alarms == null) {
            alarms = new HashMap<Integer, WidgetAlarm>();
        }

        if(alarms.containsKey(id)) {
            WidgetAlarm alarm = alarms.get(id);
            alarm.stop();
            alarms.remove(id);
        }
    }

    protected void refresh(Context context, int id) {
        if(id != AppWidgetManager.INVALID_APPWIDGET_ID) {
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            manager.notifyAppWidgetViewDataChanged(id, R.id.textlist);
        }
    }

    protected void update(Context context, int id)
    {
        if(id != AppWidgetManager.INVALID_APPWIDGET_ID)
        {
            destroyAlarm(id);
            createAlarm(context, id);

            RemoteViews view = WidgetBuilder.create(context, id);
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            manager.updateAppWidget(id, view);
        }
    }

    protected void config(Context context, int id) {
        if(id != AppWidgetManager.INVALID_APPWIDGET_ID) {
            Intent configurationIntent = new Intent(context, WidgetViewConfigure.class);
            configurationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            configurationIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
            context.startActivity(configurationIntent);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent == null) return;

        int id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        if(id == AppWidgetManager.INVALID_APPWIDGET_ID) {
            return;
        }

        String action = intent.getAction();
        Context ctx = getApplicationContext();

        if(action == null) {
            update(ctx, id);
        }
        else if(action.equals(WidgetViewConfigure.REFRESH)) {
            refresh(ctx, id);
        }
        else if(action.equals(WidgetViewConfigure.CONFIG)) {
            config(ctx, id);
        }
    }
}
