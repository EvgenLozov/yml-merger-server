package com.company;

import company.StAXService;
import company.handlers.xml.XmlEventHandler;
import company.providers.FileXMLEventReaderProvider;
import company.providers.XMLEventReaderProvider;

import javax.xml.stream.XMLStreamException;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException, XMLStreamException {
        ModifierConfig config = new ModifierConfigProvider().get();

        XMLEventReaderProvider readerProvider = new FileXMLEventReaderProvider(config.getInputFile(), config.getEncoding());

        StAXService stAXService = new StAXService( readerProvider );

        Writer writer = new OutputStreamWriter(new FileOutputStream(config.getOutputDir() + "/my.xml"), config.getEncoding());
        XmlEventHandler handler = new ModifierXmlEventHandlerProvider(config, writer).get();

        stAXService.process(handler);
        writer.flush();

    }

}
