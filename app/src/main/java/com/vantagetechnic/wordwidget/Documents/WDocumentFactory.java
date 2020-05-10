package com.vantagetechnic.wordwidget.Documents;

import java.io.File;

/**
 * Created by aaronklick on 8/12/17.
 */

public class WDocumentFactory {
    public static WDocument parse(File f) {
        if(!f.isFile() || !f.exists()) return null;

        String name = f.getName().toLowerCase();

        if(name.endsWith(".txt")) {
            return new TextDocument(f);
        }
        else if(name.endsWith(".docx")) {
            return new WordDocument(f);
        }
        else if(name.endsWith(".odt")) {
            return new ODTDocument(f);
        }
        else if(name.endsWith(".pdf")) {
            return new PDFDocument(f);
        }

        return null;
    }

    public static WDocument parse(String path) {
        return parse(new File(path));
    }
}
