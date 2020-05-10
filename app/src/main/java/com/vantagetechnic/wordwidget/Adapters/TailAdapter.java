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
 * Created by aaronklick on 8/14/17.
 */

public class TailAdapter extends ArrayAdapter<Integer> {
    Activity context;

    public static final int offset = 1;

    public TailAdapter(Activity ctx) {
        super(ctx.getApplicationContext(), R.layout.font_size_cell, createSizes());
        context = ctx;
    }

    static protected List<Integer> createSizes() {
        ArrayList<Integer> items = new ArrayList<Integer>();

        for(int i = offset; i <= 100; i++) {
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

        int fontSize = 22;

        TextView size = (TextView) view.findViewById(R.id.size);
        float psize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, fontSize, getContext().getResources().getDisplayMetrics());
        size.setTextSize(psize);
        size.setText(context.getString(R.string.tail_lines_count, position+offset));

        return view;
    }
}
