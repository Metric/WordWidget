package com.vantagetechnic.wordwidget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.dropbox.core.android.Auth;
import com.dropbox.core.v2.DbxClientV2;
import com.vantagetechnic.wordwidget.FileSystem.DropboxQuery;
import com.vantagetechnic.wordwidget.FileSystem.WFile;
import com.vantagetechnic.wordwidget.Widget.WidgetViewConfigure;

import java.util.List;

/**
 * Created by aaronklick on 8/14/17.
 */

public class DropboxSelector extends FileSelector {
    public final static String TAG = "dropbox:";

    public DropboxSelector() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String defaultPath() {
        return "";
    }

    @Override
    protected void query(String path) {
        files.clear();
        DbxClientV2 client = DropboxFactory.getClient();

        if(client != null) {
            new DropboxQuery(client, new DropboxQuery.Callback() {
                @Override
                public void onDataLoaded(List<WFile> result) {
                    files.addAll(result);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onError(Exception e) {
                    adapter.notifyDataSetChanged();
                }
            }).execute(path);
        }
    }

    @Override
    protected void init() {
        if(!hasToken()) {
            Auth.startOAuth2Authentication(DropboxSelector.this, getString(R.string.app_key));
        }
        else {
            SharedPreferences prefs = getSharedPreferences(WidgetViewConfigure.PREFS_NAME, 0);
            String accessToken = prefs.getString("access-token", null);
            if (accessToken != null) {
                DropboxFactory.getClient(accessToken);
            }
            query(path);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences(WidgetViewConfigure.PREFS_NAME, 0);
        String accessToken = prefs.getString("access-token", null);
        if (accessToken == null) {
            accessToken = Auth.getOAuth2Token();
            if (accessToken != null) {
                prefs.edit().putString("access-token", accessToken).apply();

                DropboxFactory.getClient(accessToken);

                if(path != null)
                    query(path);
            }
        }
        else {
            DropboxFactory.getClient(accessToken);
        }

        String uid = Auth.getUid();
        String storedUid = prefs.getString("user-id", null);
        if (uid != null && !uid.equals(storedUid)) {
            prefs.edit().putString("user-id", uid).apply();
        }
    }

    @Override
    protected void initClickListener() {
        fileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WFile f = files.get(i);

                if(f.isDirectory()) {
                    Intent changeDirectory = new Intent(getApplicationContext(), DropboxSelector.class);
                    changeDirectory.putExtra(EXTRA_PATH, f.getPath());
                    startActivityForResult(changeDirectory, CHANGE_REQUEST);
                }
                else {
                    Intent select = new Intent();
                    select.putExtra(EXTRA_PATH, TAG + f.getPath());
                    setResult(RESULT_OK, select);
                    finish();
                }
            }
        });
    }

    public static String getToken(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(WidgetViewConfigure.PREFS_NAME, 0);
        String accessToken = prefs.getString("access-token", null);
        return accessToken;
    }

    protected boolean hasToken() {
        SharedPreferences prefs = getSharedPreferences(WidgetViewConfigure.PREFS_NAME, 0);
        String accessToken = prefs.getString("access-token", null);
        return accessToken != null;
    }
}
