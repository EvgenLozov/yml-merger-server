package com.modifier.web.controller;

import com.company.FileDownloader;
import com.company.ModifierConfig;
import com.company.ModifierXmlEventHandlerProvider;
import company.StAXService;
import company.config.ConfigRepository;
import company.handlers.xml.XmlEventHandler;
import company.providers.FileXMLEventReaderProvider;
import company.providers.XMLEventReaderProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;


/**
 * Created by Naya on 16.01.2016.
 */

@RestController
@RequestMapping("/modifierService")
public class ModifierController {

    @Autowired
    private ConfigRepository<ModifierConfig> configRepository;

    @RequestMapping(value = "{id}/modify", method = RequestMethod.POST)
    public void modify(@PathVariable final String id) {
        Runnable modifyTask = () -> {
            ModifierConfig config = configRepository.get(id);
            if (config.getInputFileURL()!=null){
                FileDownloader fd = new FileDownloader(config.getInputFileURL(), config.getOutputDir());
                String inputFile = null;
                try {
                    inputFile = fd.download();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                config.setInputFile(inputFile);
            }

            XMLEventReaderProvider readerProvider = new FileXMLEventReaderProvider(config.getInputFile(), config.getEncoding());
            StAXService stAXService = new StAXService(readerProvider);

            try {
                XmlEventHandler handler = new ModifierXmlEventHandlerProvider(config).get();
                stAXService.process(handler);
            } catch (IOException | XMLStreamException e) {
                e.printStackTrace();
            }
        };

        new Thread(modifyTask).start();
    }
}
