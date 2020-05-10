package com.vantagetechnic.wordwidget.Documents;

import java.io.File;
import java.util.List;

/**
 * Created by aaronklick on 8/12/17.
 */

public abstract class WDocument {
    protected File file;

    public WDocument(File f) {
        file = f;
    }

    public abstract List<String> getText();
}
