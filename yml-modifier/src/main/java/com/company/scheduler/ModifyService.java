package com.company.scheduler;

import com.company.FileDownloader;
import com.company.ModifierConfig;
import com.company.ModifierXmlEventHandlerProvider;
import com.company.logger.ProcessLogger;
import company.StAXService;
import company.handlers.xml.XmlEventHandler;
import company.providers.FileXMLEventReaderProvider;
import company.providers.XMLEventReaderProvider;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Naya on 20.01.2016.
 */
public class ModifyService {

    private static final ProcessLogger logger = ProcessLogger.INSTANCE;


    public void process(ModifierConfig config)  {

        if (config.getInputFileURL()!=null){
            FileDownloader fd = new FileDownloader(config.getInputFileURL(), config.getOutputDir());
            try {
                String inputFile = fd.download();
                config.setInputFile(inputFile);
            } catch (IOException e) {
                //throw new RuntimeException("Unable to download an input file");      // unable download file
                logger.warning("Unable to do auto merge of " + config.getId());
                e.printStackTrace();
            }
        }
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
