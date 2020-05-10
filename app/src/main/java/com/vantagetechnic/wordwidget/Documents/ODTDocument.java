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

public class ODTDocument extends WDocument {
    public ODTDocument(File f) {
        super(f);
    }

    public List<String> getText() {
        ArrayList<String> paragraphs = new ArrayList<String>();

        Document doc = open();

        if(doc == null) return paragraphs;

        NodeList list = doc.getElementsByTagName("office:text");
        Element textBody = (Element)list.item(0);
        NodeList bodyChildren = textBody.getChildNodes();

        int childCount = bodyChildren.getLength();

        for(int i = 0; i < childCount; i++)
        {
            Element item = (Element)bodyChildren.item(i);

            if(item.getTagName().equals("text:p"))
            {
                paragraphs.add(item.getTextContent());
            }
            else if(item.getTagName().equals("text:h"))
            {
                paragraphs.add(item.getTextContent());
            }
        }

        return paragraphs;
    }

    protected Document open()
    {
        try
        {
            ZipFile odt = new ZipFile(this.file);
            ZipEntry entry = odt.getEntry("content.xml");
            InputStream documentXML = odt.getInputStream(entry);
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
