package com.vantagetechnic.wordwidget.Documents;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by aaronklick on 8/12/17.
 */

public class WordDocument extends WDocument {
    public WordDocument(File f) {
        super(f);
    }

    public List<String> getText() {
        ArrayList<String> paragraphs = new ArrayList<String>();

        Document doc = open();

        if(doc == null) return paragraphs;

        NodeList list = doc.getElementsByTagName("w:p");

        int length = list.getLength();

        for(int i = 0; i < length; i++)
        {
            String text = "";
            Element paragraph = (Element)list.item(i);
            NodeList wt = paragraph.getElementsByTagName("w:t");

            int textLength = wt.getLength();

            for(int n = 0; n < textLength; n++)
            {
                text += wt.item(n).getTextContent();
            }

            paragraphs.add(text);
        }

        return paragraphs;
    }

    protected Document open() {
        try
        {
            ZipFile docx = new ZipFile(this.file);
            ZipEntry entry = docx.getEntry("word/document.xml");
            InputStream documentXML = docx.getInputStream(entry);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            Document doc = dbf.newDocumentBuilder().parse(documentXML);
            documentXML.close();
            return doc;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
