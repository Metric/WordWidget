package com.vantagetechnic.wordwidget.FileSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by aaronklick on 8/12/17.
 */

public class FileQuery {
    static final String defaultTypes = ".docx,.txt,.odt,.pdf";
    String fileTypes;

    public FileQuery() {
        this(defaultTypes);
    }

    public FileQuery(String filetypes) {
        fileTypes = filetypes;
    }

    public List<WFile> find(String parent) {
        ArrayList<WFile> results = new ArrayList<WFile>();
        String[] filter = fileTypes != null ? fileTypes.split(",") : new String[0];

        File f = new File(parent);

        if(f.isDirectory()) {
            File[] files = f.listFiles();

            for (File file : files) {
                if(file.isFile()) {
                    if (matchesFilter(filter, file.getName().toLowerCase())) {
                        results.add(new WFile(file));
                    }
                }
                else {
                    results.add(new WFile(file));
                }
            }
        }

        Collections.sort(results);

        return results;
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
