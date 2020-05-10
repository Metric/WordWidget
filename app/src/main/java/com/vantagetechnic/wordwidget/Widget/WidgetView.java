package com.vantagetechnic.wordwidget.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link WidgetViewConfigure WidgetViewConfigure}
 */
public class WidgetView extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int id : appWidgetIds) {
            Intent updateIntent = new Intent(context, WidgetService.class);
            updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
            context.startService(updateIntent);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            WidgetViewConfigure.remove(context, appWidgetId);
            //remove the alarm as well
            WidgetService.destroyAlarm(appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        WidgetService.clearAlarms();
    }
}

