package com.vantagetechnic.wordwidget.Documents;

/**
 * Created by aaronklick on 8/12/17.
 */

import android.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class PDFDocument extends  WDocument
{
    static final String TAG = "WPDFDocument";

    public PDFDocument(File f)
    {
        super(f);
    }

    public List<String> getText() {
        ArrayList<String> paragraphs = new ArrayList<String>();
        StringBuilder builder = extract();
        String[] lines = builder.toString().split("\r\n");

        if(lines.length == 0) return paragraphs;

        if(lines.length == 1) {
            lines = lines[0].split("\n");
        }

        for(int i = 0; i < lines.length; i++) {
            paragraphs.add(lines[i]);
        }

        return paragraphs;
    }

    protected StringBuilder extract()
    {
        StringBuilder builder = new StringBuilder(1024);

        try
        {
            PdfReader r = new PdfReader(file.getAbsolutePath());
            int numPages = r.getNumberOfPages();

            for(int i = 1; i <= numPages; i++) {
                builder.append(PdfTextExtractor.getTextFromPage(r, i, new PDFStrategy()));
            }
            r.close();
        }
        catch (Exception e)
        {
            Log.d(TAG, e.toString());
        }

        return builder;
    }
}
