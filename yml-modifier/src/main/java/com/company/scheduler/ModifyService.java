package com.company.scheduler;

import com.company.ModifierConfig;
import com.company.ModifierXmlEventHandlerProvider;
import company.StAXService;
import company.handlers.xml.XmlEventHandler;
import company.providers.FileXMLEventReaderProvider;
import company.providers.XMLEventReaderProvider;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Naya on 20.01.2016.
 */
public class ModifyService {

    public void process(ModifierConfig config)  {

        XMLEventReaderProvider readerProvider = new FileXMLEventReaderProvider(config.getInputFile(), config.getEncoding());

        StAXService stAXService = new StAXService( readerProvider );

        try {
            XmlEventHandler handler = new ModifierXmlEventHandlerProvider(config).get();
            stAXService.process(handler);
        } catch (FileNotFoundException | UnsupportedEncodingException | XMLStreamException e) {
            e.printStackTrace();
        }

    }
}
