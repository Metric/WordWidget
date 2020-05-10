package com.vantagetechnic.wordwidget;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vantagetechnic.wordwidget.Adapters.FileAdapter;
import com.vantagetechnic.wordwidget.FileSystem.FileQuery;
import com.vantagetechnic.wordwidget.FileSystem.WFile;

import java.util.ArrayList;
import java.util.List;

public class FileSelector extends AppCompatActivity {
    public static final String EXTRA_PATH = "EXTRA_PATH";
    public static final int CHANGE_REQUEST = 1;

    protected ListView fileList;
    protected List<WFile> files;
    protected FileAdapter adapter;
    protected String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_selector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setResult(RESULT_CANCELED);

        fileList = (ListView) findViewById(R.id.filelist);

        path = null;
        Intent intent = getIntent();

        if(intent != null) {
            path = intent.getStringExtra(EXTRA_PATH);
        }

        if(path == null) {
            path = defaultPath();
        }

        files = new ArrayList<WFile>();

        adapter = new FileAdapter(this, files);
        fileList.setAdapter(adapter);

        initClickListener();
        init();
    }

    protected void init() {
        query(path);
    }

    protected void initClickListener() {
        fileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WFile f = files.get(i);

                if(f.isDirectory()) {
                    Intent changeDirectory = new Intent(getApplicationContext(), FileSelector.class);
                    changeDirectory.putExtra(EXTRA_PATH, f.getPath());
                    startActivityForResult(changeDirectory, CHANGE_REQUEST);
                }
                else {
                    Intent select = new Intent();
                    select.putExtra(EXTRA_PATH, f.getPath());
                    setResult(RESULT_OK, select);
                    finish();
                }
            }
        });
    }

    protected void query(String path) {
        FileQuery query = new FileQuery();
        files.clear();
        files.addAll(query.find(path));
        adapter.notifyDataSetChanged();
    }

    protected String defaultPath() {
        return "/";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == CHANGE_REQUEST) {
            setResult(resultCode, data);
            finish();
        }
    }
}
