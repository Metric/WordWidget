package com.vantagetechnic.wordwidget.Widget;

import android.Manifest;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.vantagetechnic.wordwidget.ColorSelector;
import com.vantagetechnic.wordwidget.DropboxSelector;
import com.vantagetechnic.wordwidget.FileSelector;
import com.vantagetechnic.wordwidget.FontSize;
import com.vantagetechnic.wordwidget.R;
import com.vantagetechnic.wordwidget.RefreshInterval;
import com.vantagetechnic.wordwidget.TailSize;

import java.io.File;

/**
 * The configuration screen for the {@link WidgetView WidgetView} AppWidget.
 */
public class WidgetViewConfigure extends AppCompatActivity {

    public static final String PREFS_NAME = "com.vantagetechnic.wordwidget";
    private static final String PREF_PREFIX_KEY = "wordwidget";
    private static final String PREF_INTERVAL = PREF_PREFIX_KEY + "interval_";

    private static final String PREF_FILE = PREF_PREFIX_KEY + "file_";
    private static final String PREF_TEXT = PREF_PREFIX_KEY + "text_";
    private static final String PREF_FONT_SIZE = PREF_PREFIX_KEY + "font_size_";

    private static final String PREF_TEXT_COLOR = PREF_PREFIX_KEY + "text_color_";
    private static final String PREF_BACKGROUND = PREF_PREFIX_KEY + "background_";

    private static final String PREF_TAIL_SIZE = PREF_PREFIX_KEY + "tail_size_";
    private static final String PREF_TAIL = PREF_PREFIX_KEY + "tail_";

    private static final String PREF_ALIGNMENT = PREF_PREFIX_KEY + "alignment_";

    private static final int READ_PERMISSION_REQUEST = 1;
    private static final int WRITE_PERMISSION_REQUEST = 2;

    public static final String REFRESH = "com.vantagetechnic.wordwidget.Refresh";
    public static final String CONFIG = "com.vantagetechnic.wordwidget.Config";

    //activity request codes
    static final int REQUEST_FILE = 3;
    static final int REQUEST_BACKGROUND_COLOR = 4;
    static final int REQUEST_TEXT_COLOR = 5;
    static final int REQUEST_FONT_SIZE = 6;
    static final int REQUEST_TAILING = 7;
    static final int REQUEST_INTERVAL = 8;

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    /////
    ///// UI Buttons and Labels
    /////

    private TextView filenameText;
    private AppCompatButton dropboxFile;
    private AppCompatButton deviceFile;

    private TextView fontSizeText;
    private AppCompatButton selectFontSize;

    private TextView textAlignmentText;
    private AppCompatButton selectTextAlignment;

    private View textColorPreview;
    private AppCompatButton selectTextColor;

    private View backgroundColorPreview;
    private AppCompatButton selectBackgroundColor;

    private TextView tailSizeText;
    private SwitchCompat tailSizeEnabled;
    private AppCompatButton selectTailSize;

    private TextView refreshText;
    private AppCompatButton selectRefresh;

    private AppCompatButton saveButton;

    public static int getInterval(Context context, int id) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        int interval = prefs.getInt(PREF_INTERVAL + id, 10);
        return interval;
    }

    public static void setInterval(Context context, int id, int interval) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putInt(PREF_INTERVAL + id, interval);
        prefs.apply();
    }

    public static String getFile(Context context, int id) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String f = prefs.getString(PREF_FILE + id, null);
        return f;
    }

    public static void setFile(Context context, int id, String file) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_FILE + id, file);
        prefs.apply();
    }

    /*public static String getText(Context context, int id) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String f = prefs.getString(PREF_TEXT + id, null);
        return f;
    }

    public static void setText(Context context, int id, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_TEXT + id, text);
        prefs.apply();
    }*/

    public static int getFontSize(Context context, int id) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        int size = prefs.getInt(PREF_FONT_SIZE + id, 10);
        return size;
    }

    public static void setFontSize(Context context, int id, int size) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putInt(PREF_FONT_SIZE + id, size);
        prefs.apply();
    }

    public static int getTextColor(Context context, int id) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        int size = prefs.getInt(PREF_TEXT_COLOR + id, 0xFFFFFFFF);
        return size;
    }

    public static void setTextColor(Context context, int id, int size) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putInt(PREF_TEXT_COLOR + id, size);
        prefs.apply();
    }

    public static int getBackgroundColor(Context context, int id) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        int size = prefs.getInt(PREF_BACKGROUND + id, 0xFF000000);
        return size;
    }

    public static void setBackgroundColor(Context context, int id, int size) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putInt(PREF_BACKGROUND + id, size);
        prefs.apply();
    }

    public static int getTailSize(Context context, int id) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        int size = prefs.getInt(PREF_TAIL_SIZE + id, 10);
        return size;
    }

    public static void setTailSize(Context context, int id, int size) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putInt(PREF_TAIL_SIZE + id, size);
        prefs.apply();
    }

    public static boolean getTailEnabled(Context context, int id) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        boolean enabled = prefs.getBoolean(PREF_TAIL + id, false);
        return enabled;
    }

    public static void setTailEnabled(Context context, int id, boolean enabled) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putBoolean(PREF_TAIL + id, enabled);
        prefs.apply();
    }

    public static int getAlignment(Context context, int id) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        int size = prefs.getInt(PREF_ALIGNMENT + id, 0);
        return size;
    }

    public static void setAlignment(Context context, int id, int align) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putInt(PREF_ALIGNMENT + id, align);
        prefs.apply();
    }

    public static void remove(Context context, int id) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_INTERVAL + id);
        prefs.remove(PREF_FILE + id);
        prefs.remove(PREF_FONT_SIZE + id);
        prefs.remove(PREF_TEXT_COLOR + id);
        prefs.apply();
    }

    protected void checkReadPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if(permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_PERMISSION_REQUEST);
        }
    }

    protected void checkWritePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_PERMISSION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case READ_PERMISSION_REQUEST:
            case WRITE_PERMISSION_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    finish();
                }
                return;
            }
        }
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);
        setContentView(R.layout.activity_widget_view_configure);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }

        setupButtonsAndLabels();
        setPreviewsAndLabels();

        //check for permissions
        checkReadPermission();
    }

    protected void setupButtonsAndLabels() {
        filenameText = (TextView)findViewById(R.id.filename_text);
        dropboxFile = (AppCompatButton)findViewById(R.id.dropbox_file);
        deviceFile = (AppCompatButton)findViewById(R.id.device_file);

        fontSizeText = (TextView)findViewById(R.id.font_size_text);
        selectFontSize = (AppCompatButton)findViewById(R.id.select_font_size);

        textAlignmentText = (TextView)findViewById(R.id.text_alignment_text);
        selectTextAlignment = (AppCompatButton)findViewById(R.id.select_text_alignment);

        textColorPreview = findViewById(R.id.text_color_preview);
        selectTextColor = (AppCompatButton)findViewById(R.id.select_text_color);

        backgroundColorPreview = findViewById(R.id.background_color_preview);
        selectBackgroundColor = (AppCompatButton)findViewById(R.id.select_background_color);

        tailSizeText = (TextView)findViewById(R.id.tail_size_text);
        selectTailSize = (AppCompatButton)findViewById(R.id.select_tail_size);
        tailSizeEnabled = (SwitchCompat)findViewById(R.id.tail_size_enabled);

        refreshText = (TextView)findViewById(R.id.text_refresh);
        selectRefresh = (AppCompatButton)findViewById(R.id.select_refresh);

        saveButton = (AppCompatButton)findViewById(R.id.save);

        setupButtonEvents();
    }

    protected void setupButtonEvents() {
        final WidgetViewConfigure _this = this;
        dropboxFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DropboxSelector.class);
                startActivityForResult(intent, REQUEST_FILE);
            }
        });
        deviceFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FileSelector.class);
                startActivityForResult(intent, REQUEST_FILE);
            }
        });
        selectFontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FontSize.class);
                startActivityForResult(intent, REQUEST_FONT_SIZE);
            }
        });
        selectTextAlignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int align = WidgetViewConfigure.getAlignment(getApplicationContext(), _this.mAppWidgetId);
                align++;
                align = align % 3;
                WidgetViewConfigure.setAlignment(getApplicationContext(), _this.mAppWidgetId, align);
                _this.setPreviewsAndLabels();
            }
        });
        selectTextColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ColorSelector.class);
                startActivityForResult(intent, REQUEST_TEXT_COLOR);
            }
        });
        selectBackgroundColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ColorSelector.class);
                startActivityForResult(intent, REQUEST_BACKGROUND_COLOR);
            }
        });
        tailSizeEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                WidgetViewConfigure.setTailEnabled(getApplicationContext(), _this.mAppWidgetId, b);
            }
        });
        selectTailSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TailSize.class);
                startActivityForResult(intent, REQUEST_TAILING);
            }
        });
        selectRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RefreshInterval.class);
                startActivityForResult(intent, REQUEST_INTERVAL);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
            }
        });
    }

    protected void setPreviewsAndLabels() {
        Resources res = getResources();
        int bgc = getBackgroundColor(getApplicationContext(), mAppWidgetId);
        int tc = getTextColor(getApplicationContext(), mAppWidgetId);
        int fontsize = getFontSize(getApplicationContext(), mAppWidgetId);
        int refresh = getInterval(getApplicationContext(), mAppWidgetId);
        int tailSize = getTailSize(getApplicationContext(), mAppWidgetId);
        boolean tailEnabled = getTailEnabled(getApplicationContext(), mAppWidgetId);
        int alignment = getAlignment(getApplicationContext(), mAppWidgetId);

        String path = getFile(getApplicationContext(), mAppWidgetId);
        String filename = "NONE";

        if(path != null || path.length() > 0) {
            if(path.startsWith(DropboxSelector.TAG)) {
                path = path.replace(DropboxSelector.TAG, "");
                File f = new File(path);
                filename = DropboxSelector.TAG + f.getName();
            }
            else {
                File f = new File(path);
                filename = f.getName();
            }
        }

        switch(alignment) {
            case 1:
                textAlignmentText.setText(res.getString(R.string.text_alignment_select, "CENTER"));
                break;
            case 2:
                textAlignmentText.setText(res.getString(R.string.text_alignment_select, "RIGHT"));
                break;
            case 0:
            default:
                textAlignmentText.setText(res.getString(R.string.text_alignment_select, "LEFT"));
                break;
        }

        filenameText.setText(res.getString(R.string.file_name, filename));

        backgroundColorPreview.setBackgroundColor(bgc);
        textColorPreview.setBackgroundColor(tc);

        fontSizeText.setText(res.getString(R.string.font_size_select, fontsize));
        refreshText.setText(res.getString(R.string.refresh_minutes, refresh));
        tailSizeText.setText(res.getString(R.string.tail_size_select, tailSize));
        tailSizeEnabled.setChecked(tailEnabled);
    }

    //Activity Result for various things
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == REQUEST_BACKGROUND_COLOR) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                //save the background color
                int c = data.getIntExtra(ColorSelector.EXTRA_COLOR, 0xFF000000);
                setBackgroundColor(getApplicationContext(), mAppWidgetId, c);
                //update preview
                setPreviewsAndLabels();
            }
        }
        else if(requestCode == REQUEST_TEXT_COLOR) {
            if(resultCode == RESULT_OK) {
                int c = data.getIntExtra(ColorSelector.EXTRA_COLOR, 0xFFFFFFFF);
                setTextColor(getApplicationContext(), mAppWidgetId, c);
                //update preview
                setPreviewsAndLabels();
            }
        }
        else if(requestCode == REQUEST_FONT_SIZE) {
            if(resultCode == RESULT_OK) {
                int size = data.getIntExtra(FontSize.EXTRA_FONT_SIZE, 10);
                setFontSize(getApplicationContext(), mAppWidgetId, size);
                //update label
                setPreviewsAndLabels();
            }
        }
        else if(requestCode == REQUEST_TAILING) {
            if(resultCode == RESULT_OK) {
                int size = data.getIntExtra(TailSize.EXTRA_TAIL_SIZE, 10);
                setTailSize(getApplicationContext(), mAppWidgetId, size);
                //update label
                setPreviewsAndLabels();
            }
        }
        else if(requestCode == REQUEST_FILE) {
            if(resultCode == RESULT_OK) {
                String path = data.getStringExtra(FileSelector.EXTRA_PATH);
                setFile(getApplicationContext(), mAppWidgetId, path);
                //update label
                setPreviewsAndLabels();
            }
        }
        else if(requestCode == REQUEST_INTERVAL) {
            if(resultCode == RESULT_OK) {
                int interval = data.getIntExtra(RefreshInterval.EXTRA_INTERVAL, 10);
                setInterval(getApplicationContext(), mAppWidgetId, interval);
                //update label
                setPreviewsAndLabels();
            }
        }
    }
}

