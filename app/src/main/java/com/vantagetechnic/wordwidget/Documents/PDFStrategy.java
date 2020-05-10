package com.vantagetechnic.wordwidget.Documents;

/**
 * Created by aaronklick on 8/12/17.
 */

import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.Vector;

public class PDFStrategy extends LocationTextExtractionStrategy {

    public PDFStrategy() {
        super();
    }

    @Override
    protected boolean isChunkAtWordBoundary(TextChunk chunk, TextChunk previousChunk) {
        Vector v1 = chunk.getStartLocation();
        Vector v2 = previousChunk.getEndLocation();

        Vector diff = v1.subtract(v2);

        float x = diff.get(Vector.I1);
        float y = diff.get(Vector.I2);
        float space = chunk.getCharSpaceWidth();

        //this is the best formula that works
        //with consistency across many different types of
        //formatted pdf documents
        return x >= space / 5.0f && y < 16;
    }
}
