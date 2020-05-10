package com.vantagetechnic.wordwidget.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.vantagetechnic.wordwidget.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaronklick on 8/12/17.
 */

public class WidgetListAdapter extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewRemoteViewFactory(getApplicationContext(), intent);
    }

    public class ListViewRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
        private Context context;
        private int id;
        private List<String> paragraphs;

        public ListViewRemoteViewFactory(Context context, Intent intent) {
            this.context = context;
            this.paragraphs = new ArrayList<String>();
            this.id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public int getCount() {
            return this.paragraphs.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public RemoteViews getLoadingView() {
            RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.widget_row);

            WidgetBuilder.fill(view, context, id, "Loading...");

            return view;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews view = null;

            view = new RemoteViews(context.getPackageName(), R.layout.widget_row);

            WidgetBuilder.fill(view, context, id, paragraphs.get(position));

            return view;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            this.paragraphs = WidgetBuilder.getText(context, id);
        }

        @Override
        public void onDestroy() {

        }
    }
}
