package com.vantagetechnic.wordwidget.FileSystem;

import android.os.AsyncTask;

import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by aaronklick on 8/14/17.
 */

public class DropboxQuery extends AsyncTask<String, Void, List<WFile>> {

    private final DbxClientV2 mDbxClient;
    private final Callback mCallback;
    private Exception mException;

    static final String defaultTypes = ".docx,.txt,.odt,.pdf";
    String fileTypes;
    String[] filter;

    public DropboxQuery(DbxClientV2 dbxClient, Callback callback) {
        this(defaultTypes, dbxClient, callback);
    }

    public DropboxQuery(String types, DbxClientV2 dbxClient, Callback callback) {
        mDbxClient = dbxClient;
        mCallback = callback;
        fileTypes = types;
        filter = fileTypes.split(",");
    }

    public interface Callback {
        void onDataLoaded(List<WFile> result);

        void onError(Exception e);
    }

    @Override
    protected void onPostExecute(List<WFile> result) {
        super.onPostExecute(result);

        if (mException != null) {
            mCallback.onError(mException);
        } else {
            mCallback.onDataLoaded(result);
        }
    }

    @Override
    protected List<WFile> doInBackground(String... params) {
        ArrayList<WFile> finalResults = new ArrayList<WFile>();
        try {
            ListFolderResult results = mDbxClient.files().listFolder(params[0]);

            List<Metadata> files = results.getEntries();

            for (Metadata file : files) {
                if(file instanceof FileMetadata) {
                    if(matchesFilter(filter, file.getName().toLowerCase())) {
                        finalResults.add(new WFile(file));
                    }
                }
                else if(file instanceof FolderMetadata) {
                    finalResults.add(new WFile(file));
                }
            }

            Collections.sort(finalResults);
        }
        catch (Exception e) {
            mException = e;
        }

        return finalResults;
    }

    protected boolean matchesFilter(String[] filter, String name) {
        for (String match : filter) {
            if(name.endsWith(match)) {
                return true;
            }
        }

        return false;
    }
}
