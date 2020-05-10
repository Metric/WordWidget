package com.vantagetechnic.wordwidget.FileSystem;

import android.os.AsyncTask;

import com.dropbox.core.v2.DbxClientV2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by aaronklick on 8/14/17.
 */

public class DropboxDownload {

    DbxClientV2 mClient;
    File mPath;

    public DropboxDownload(File path, DbxClientV2 client) {
        mClient = client;
        mPath = path;
    }

    /***
     * Id is the widget id
     * Path is the dropbox path
     * @param path
     * @param id
     * @return File
     */
    public File download(String path, int id) {
        File df = new File(path);
        String name = id + "-" + df.getName();

        File outpath = new File(mPath, name);

        // Download the file.
        try (OutputStream outputStream = new FileOutputStream(outpath)) {
            mClient.files().download(path)
                    .download(outputStream);
        }
        catch (Exception e) {

        }

        return outpath;
    }

    /*@Override
    protected void onPostExecute(File result) {
        super.onPostExecute(result);

        if (mException != null) {
            mCallback.onError(mException);
        } else {
            mCallback.onDownload(result);
        }
    }

    @Override
    protected File doInBackground(String... params) {
        try {
            File df = new File(params[0]);
            String name = df.getName();

            File outpath = new File(mPath, name);

            // Download the file.
            try (OutputStream outputStream = new FileOutputStream(outpath)) {
                mClient.files().download(params[0])
                        .download(outputStream);
            }

            return outpath;
        } catch (Exception e) {
            mException = e;
        }

        return null;
    }*/
}
