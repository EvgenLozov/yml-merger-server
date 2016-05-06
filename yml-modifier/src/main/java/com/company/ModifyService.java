package com.company;

import company.StAXService;
import company.handlers.xml.XmlEventHandler;
import company.providers.ByteArrayXmlEventReaderProvider;
import company.providers.XMLEventReaderProvider;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Naya on 20.01.2016.
 */
public class ModifyService {

    public void process(ModifierConfig config)  {
        byte[] inputXmlBytes = new ModifierXmlBytesProvider(config).get();
        XMLEventReaderProvider readerProvider = new ByteArrayXmlEventReaderProvider(inputXmlBytes, config.getEncoding());
        StAXService stAXService = new StAXService( readerProvider );

        try {
            XmlEventHandler handler = new ModifierXmlEventHandlerProvider(config, readerProvider).get();
            stAXService.process(handler);

        } catch (FileNotFoundException | UnsupportedEncodingException | XMLStreamException e) {
            throw new RuntimeException(e);
        }

    }
}
