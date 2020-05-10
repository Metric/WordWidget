package com.vantagetechnic.wordwidget.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.TypedValue;
import android.view.View;
import android.widget.RemoteViews;

import com.dropbox.core.v2.DbxClientV2;
import com.vantagetechnic.wordwidget.Documents.WDocument;
import com.vantagetechnic.wordwidget.Documents.WDocumentFactory;
import com.vantagetechnic.wordwidget.DropboxFactory;
import com.vantagetechnic.wordwidget.DropboxSelector;
import com.vantagetechnic.wordwidget.FileSystem.DropboxDownload;
import com.vantagetechnic.wordwidget.R;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaronklick on 8/14/17.
 */

//TODO: Add refresh button to widget view
//TODO: Add config button to widget view

public class WidgetBuilder {
    public static RemoteViews create(Context ctx, int id) {
        RemoteViews view = null;

        int backgroundColor = WidgetViewConfigure.getBackgroundColor(ctx, id);

        view = new RemoteViews(ctx.getPackageName(), R.layout.widget_view);

        //setup list view
        Intent listViewIntent = new Intent(ctx, WidgetListAdapter.class);
        listViewIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
        listViewIntent.setData(Uri.parse(listViewIntent.toUri(Intent.URI_INTENT_SCHEME)));

        view.setRemoteAdapter(R.id.textlist, listViewIntent);

        //set background
        view.setInt(R.id.textlist, "setBackgroundColor", backgroundColor);

        Intent refreshIntent = new Intent(ctx, WidgetService.class);
        refreshIntent.setAction(WidgetViewConfigure.REFRESH);
        refreshIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
        PendingIntent refreshAction = PendingIntent.getBroadcast(ctx, id, refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        view.setOnClickPendingIntent(R.id.refresh, refreshAction);

        Intent configIntent = new Intent(ctx, WidgetService.class);
        configIntent.setAction(WidgetViewConfigure.CONFIG);
        configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
        PendingIntent configAction = PendingIntent.getBroadcast(ctx, id, configIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        view.setOnClickPendingIntent(R.id.config, configAction);

        return view;
    }

    public static void fill(RemoteViews view, Context ctx, int id, String text) {
        int textColor = WidgetViewConfigure.getTextColor(ctx, id);
        int alignment = WidgetViewConfigure.getAlignment(ctx, id);
        int fontSize = WidgetViewConfigure.getFontSize(ctx, id);

        float psize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, fontSize, ctx.getResources().getDisplayMetrics());

        view.setViewVisibility(R.id.text, View.GONE);
        view.setViewVisibility(R.id.textcenter, View.GONE);
        view.setViewVisibility(R.id.textright, View.GONE);

        if(alignment == 0) {
            view.setViewVisibility(R.id.text, View.VISIBLE);
            view.setTextViewText(R.id.text, text);
            view.setTextViewTextSize(R.id.text, TypedValue.COMPLEX_UNIT_DIP, psize);
            view.setTextColor(R.id.text, textColor);
        }
        else if(alignment == 1) {
            view.setViewVisibility(R.id.textcenter, View.VISIBLE);
            view.setTextViewText(R.id.textcenter, text);
            view.setTextViewTextSize(R.id.textcenter, TypedValue.COMPLEX_UNIT_DIP, psize);
            view.setTextColor(R.id.textcenter, textColor);
        }
        else if(alignment == 2) {
            view.setViewVisibility(R.id.textright, View.VISIBLE);
            view.setTextViewText(R.id.textright, text);
            view.setTextViewTextSize(R.id.textright, TypedValue.COMPLEX_UNIT_DIP, psize);
            view.setTextColor(R.id.textright, textColor);
        }
        else {
            view.setViewVisibility(R.id.text, View.VISIBLE);
            view.setTextViewText(R.id.text, text);
            view.setTextViewTextSize(R.id.text, TypedValue.COMPLEX_UNIT_DIP, psize);
            view.setTextColor(R.id.text, textColor);
        }
    }

    public static List<String> getText(Context ctx, int id) {
        File cachePath = ctx.getCacheDir();
        String filePath = WidgetViewConfigure.getFile(ctx, id);

        //exit early
        if(filePath == null || filePath.length() == 0) {
            return new ArrayList<String>();
        }

        File f = null;

        if(filePath.startsWith(DropboxSelector.TAG)) {
            String dbpath = filePath.replace(DropboxSelector.TAG, "");
            String token = DropboxSelector.getToken(ctx);
            if(token == null) {
                return new ArrayList<String>();
            }
            DbxClientV2 c = DropboxFactory.getClient(token);
            f = (new DropboxDownload(cachePath, c)).download(dbpath, id);
        }
        else {
            f = new File(filePath);
        }

        WDocument doc = WDocumentFactory.parse(f);

        //failed to parse valid file type
        //exit
        if(doc == null) {
            return new ArrayList<String>();
        }

        List<String> lines = doc.getText();

        //is tailing enabled?
        if(WidgetViewConfigure.getTailEnabled(ctx, id)) {
            int tailLimit = WidgetViewConfigure.getTailSize(ctx, id);

            if(tailLimit < 1) tailLimit = 1;

            //get last number of lines
            lines = lines.subList(lines.size() - tailLimit - 1, lines.size() - 1);
        }

        return lines;
    }
}
