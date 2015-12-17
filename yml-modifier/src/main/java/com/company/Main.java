package com.company;

import company.StAXService;
import company.conditions.InElementCondition;
import company.handlers.xml.EventCounter;
import company.handlers.xml.XmlEventHandler;
import company.providers.FileXMLEventReaderProvider;
import company.providers.XMLEventReaderProvider;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, XMLStreamException {
        ModifierConfig config = new ModifierConfigProvider().get();

        XMLEventReaderProvider readerProvider = new FileXMLEventReaderProvider(config.getInputFile(), config.getEncoding());

        StAXService stAXService = new StAXService( readerProvider );

        XmlEventHandler handler = new ModifierXmlEventHandlerProvider(config).get();

        stAXService.process(handler);
    }



}
