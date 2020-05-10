package com.vantagetechnic.wordwidget.Adapters;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vantagetechnic.wordwidget.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaronklick on 8/12/17.
 */

public class FontAdapter extends ArrayAdapter<Integer> {
    Activity context;

    public static int offset = 8;

    public FontAdapter(Activity ctx) {
        super(ctx.getApplicationContext(), R.layout.font_size_cell, createSizes());
        context = ctx;
    }

    static protected List<Integer> createSizes() {
        ArrayList<Integer> items = new ArrayList<Integer>();

        for(int i = offset; i <= 34; i++) {
            items.add(i);
        }

        return items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if(convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.font_size_cell, null);
        }
        else {
            view = convertView;
        }

        int fontSize = position + offset;

        TextView size = (TextView) view.findViewById(R.id.size);
        float psize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, fontSize, context.getResources().getDisplayMetrics());
        size.setTextSize(psize);
        size.setText(fontSize);

        return view;
    }
}
