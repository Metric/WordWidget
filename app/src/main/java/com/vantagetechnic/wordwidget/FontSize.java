package com.vantagetechnic.wordwidget;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vantagetechnic.wordwidget.Adapters.FontAdapter;

public class FontSize extends AppCompatActivity {
    public static final String EXTRA_FONT_SIZE = "EXTRA_FONT_SIZE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_size);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setResult(RESULT_CANCELED);

        ListView lview = (ListView) findViewById(R.id.listview);

        FontAdapter fontAdapter = new FontAdapter(this);
        lview.setAdapter(fontAdapter);
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent result = new Intent();
                result.putExtra(EXTRA_FONT_SIZE, i+FontAdapter.offset);
                setResult(RESULT_OK, result);
                finish();
            }
        });
        fontAdapter.notifyDataSetChanged();
    }
}
