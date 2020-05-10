package com.vantagetechnic.wordwidget;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vantagetechnic.wordwidget.Adapters.TailAdapter;

/**
 * Created by aaronklick on 8/14/17.
 */

public class TailSize extends AppCompatActivity {
    public static final String EXTRA_TAIL_SIZE = "EXTRA_TAIL_SIZE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_size);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setResult(RESULT_CANCELED);

        ListView lview = (ListView) findViewById(R.id.listview);

        TailAdapter fontAdapter = new TailAdapter(this);
        lview.setAdapter(fontAdapter);
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent result = new Intent();
                result.putExtra(EXTRA_TAIL_SIZE, i+TailAdapter.offset);
                setResult(RESULT_OK, result);
                finish();
            }
        });
        fontAdapter.notifyDataSetChanged();
    }
}
