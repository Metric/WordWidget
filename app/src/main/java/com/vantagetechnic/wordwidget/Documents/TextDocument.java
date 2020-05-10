package com.vantagetechnic.wordwidget.Documents;

import android.util.Log;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaronklick on 8/12/17.
 */

public class TextDocument extends WDocument {
    public TextDocument(File f) {
        super(f);
    }

    public List<String> getText() {
        ArrayList<String> paragraphs = new ArrayList<String>();

        try {
            String line = "";
            byte[] buf = new byte[4096];
            java.io.FileInputStream fis = new java.io.FileInputStream(file);

            // (1)
            UniversalDetector detector = new UniversalDetector(null);

            // (2)
            int nread;
            while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
            // (3)
            detector.dataEnd();

            fis.close();

            // (4)
            String encoding = detector.getDetectedCharset();

            BufferedReader reader = null;

            if (encoding != null) {
                Log.d("Encoding", "Found Encoding: " + encoding);
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
            } else {
                Log.d("Encoding", "No Encoding Found Using UTF-8");
                //Couldn't detect so we will just use UTF-8
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            }

            // (5)
            detector.reset();

            while((line = reader.readLine()) != null)
            {
                paragraphs.add(line);
            }

        }
        catch (Exception e) {

        }

        return paragraphs;
    }
}
