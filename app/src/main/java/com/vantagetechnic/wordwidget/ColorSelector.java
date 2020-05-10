package com.vantagetechnic.wordwidget;

import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;

public class ColorSelector extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    public static final String EXTRA_COLOR = "EXTRA_COLOR";

    AppCompatButton selectButton;
    AppCompatButton cancelButton;

    AppCompatSeekBar red;
    AppCompatSeekBar blue;
    AppCompatSeekBar green;
    AppCompatSeekBar alpha;

    View preview;

    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_selector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setResult(RESULT_CANCELED);

        preview = findViewById(R.id.color_preview);
        selectButton = (AppCompatButton)findViewById(R.id.select_color);
        cancelButton = (AppCompatButton)findViewById(R.id.cancel);

        red = (AppCompatSeekBar)findViewById(R.id.red);
        green = (AppCompatSeekBar)findViewById(R.id.green);
        blue = (AppCompatSeekBar)findViewById(R.id.blue);
        alpha = (AppCompatSeekBar)findViewById(R.id.alpha);

        red.setOnSeekBarChangeListener(this);
        green.setOnSeekBarChangeListener(this);
        blue.setOnSeekBarChangeListener(this);
        alpha.setOnSeekBarChangeListener(this);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent result = new Intent();
                result.putExtra(EXTRA_COLOR, color);
                setResult(RESULT_OK, result);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent sintent = getIntent();
        color = sintent.getIntExtra(EXTRA_COLOR, 0xFF000000);
        setStartColor();
    }

    void setStartColor() {
        int a = (color>>24)&0xFF;
        int r = (color>>16)&0XFF;
        int g = (color>>8)&0xFF;
        int b = (color>>0)&0xFF;

        alpha.setProgress(a);
        red.setProgress(r);
        green.setProgress(g);
        blue.setProgress(b);

        preview.setBackgroundColor(color);
    }

    void update() {
        color = Color.argb(alpha.getProgress(), red.getProgress(), green.getProgress(), blue.getProgress());
        preview.setBackgroundColor(color);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        update();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //not needed
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //not needed
    }
}
