package com.company;

import company.StAXService;
import company.handlers.xml.XmlEventHandler;
import company.providers.FileXMLEventReaderProvider;
import company.providers.XMLEventReaderProvider;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.util.Base64;

public class Main {

    public static void main(String[] args) throws IOException, XMLStreamException {
        /*ModifierConfigProperties config = new ModifierConfigProvider().get();

        XMLEventReaderProvider readerProvider = new FileXMLEventReaderProvider(config.getInputFile(), config.getEncoding());

        StAXService stAXService = new StAXService( readerProvider );

        XmlEventHandler handler = new ModifierPropertiesXmlEventHandlerProvider(config).get();

        stAXService.process(handler);*/
        String psw = new String(Base64.getDecoder().decode("bmV1c2VyNTA=".getBytes()));
        System.out.print(psw);
    }



}
