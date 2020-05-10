package com.vantagetechnic.wordwidget;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.http.OkHttp3Requestor;
import com.dropbox.core.v2.DbxClientV2;

/**
 * Created by aaronklick on 8/14/17.
 */

public class DropboxFactory {
    private static DbxClientV2 sDbxClient;

    public static DbxClientV2 getClient() {
        return sDbxClient;
    }

    public static DbxClientV2 getClient(String accessToken) {
        if (sDbxClient == null && accessToken != null) {
            DbxRequestConfig requestConfig = DbxRequestConfig.newBuilder("wordwidget")
                    .withHttpRequestor(new OkHttp3Requestor(OkHttp3Requestor.defaultOkHttpClient()))
                    .build();

            sDbxClient = new DbxClientV2(requestConfig, accessToken);
        }

        return sDbxClient;
    }
}
