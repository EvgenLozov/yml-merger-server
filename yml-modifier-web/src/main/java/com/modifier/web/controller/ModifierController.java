package com.modifier.web.controller;

import com.company.FileDownloader;
import com.company.ModifierConfig;
import com.company.ModifierXmlEventHandlerProvider;
import com.company.scheduler.ModifyService;
import company.StAXService;
import company.config.ConfigRepository;
import company.handlers.xml.XmlEventHandler;
import company.providers.FileXMLEventReaderProvider;
import company.providers.XMLEventReaderProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * Created by Naya on 16.01.2016.
 */

@RestController
@RequestMapping("/modifierService")
public class ModifierController {

    @Autowired
    private ConfigRepository<ModifierConfig> configRepository;

    @RequestMapping(value = "{id}/modify", method = RequestMethod.POST)
    public void modify(@PathVariable final String id) throws XMLStreamException {
        Runnable modifyTask = () -> {
            ModifierConfig config = configRepository.get(id);
            if (config.getInputFileURL()!=null){
                FileDownloader fd = new FileDownloader(config.getInputFileURL(), config.getOutputDir());
                try {
                    String inputFile = fd.download();
                    config.setInputFile(inputFile);
                } catch (IOException e) {
                    throw new RuntimeException("Unable to download an input file");      // unable download file
                }

            }

            ModifyService modifyService = new ModifyService();
            modifyService.process(config);

        };

        new Thread(modifyTask).start();
    }
}
