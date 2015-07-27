package com.company.readerproviders;

import company.XMLEventReaderProvider;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by user50 on 04.07.2015.
 */
public class FileXMLEventReaderProvider implements XMLEventReaderProvider {
    String fileName;
    String encoding;

    public FileXMLEventReaderProvider(String fileName, String encoding) {
        this.fileName = fileName;
        this.encoding = encoding;
    }

    @Override
    public XMLEventReader get() {
        XMLInputFactory ifactory = XMLInputFactory.newFactory();
        ifactory.setProperty(XMLInputFactory.IS_VALIDATING, false);
        try {
            return ifactory.createXMLEventReader(new FileInputStream(fileName), encoding);
        } catch (XMLStreamException e) {
            throw new RuntimeException("Unable to process file " + fileName + "\n" + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can not find file " + fileName);
        }
    }

    @Override
    public String toString() {
        return "File: "+fileName;
    }
}
