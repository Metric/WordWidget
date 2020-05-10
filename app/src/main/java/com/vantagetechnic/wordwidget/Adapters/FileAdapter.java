package com.vantagetechnic.wordwidget.Adapters;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vantagetechnic.wordwidget.FileSystem.WFile;
import com.vantagetechnic.wordwidget.R;

import java.util.List;

/**
 * Created by aaronklick on 8/12/17.
 */

public class FileAdapter extends ArrayAdapter<WFile> {
    List<WFile> files;
    Activity context;

    public FileAdapter(Activity ctx, List<WFile> files) {
        super(ctx.getApplicationContext(), R.layout.file_row_view, files);
        this.files = files;
        context = ctx;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if(convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.file_row_view, null);
        }
        else {
            view = convertView;
        }

        WFile f = files.get(position);

        if(f.isDirectory()) {
            view.findViewById(R.id.directorytype).setVisibility(View.VISIBLE);
            view.findViewById(R.id.filetype).setVisibility(View.GONE);
        }
        else {
            view.findViewById(R.id.directorytype).setVisibility(View.GONE);
            view.findViewById(R.id.filetype).setVisibility(View.VISIBLE);
        }

        TextView filename = (TextView) view.findViewById(R.id.filename);
        filename.setText(f.getName());

        return view;
    }
}
