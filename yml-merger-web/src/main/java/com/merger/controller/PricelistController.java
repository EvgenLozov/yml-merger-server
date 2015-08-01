package com.merger.controller;

import com.company.MergeService;
import com.company.config.Config;
import com.merger.ConfigRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import java.io.*;

/**
 * @author lozov
 */
@RestController
@RequestMapping("/pricelists")
class PricelistController {

    @Autowired
    private ConfigRepository configRepository;

    MergeService mergeService = new MergeService();

    @RequestMapping(value = "/{id}/merge", method = RequestMethod.POST)
    public void merge(@PathVariable final String id) throws FileNotFoundException, XMLStreamException {
        Runnable mergeTask = new Runnable() {
            public void run() {
                Config config = configRepository.get(id);
                try {
                    mergeService.process(config);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                }

                config.setLastMerge(System.currentTimeMillis());
                configRepository.save(config);
            }
        };

        new Thread(mergeTask).start();
    }

    @RequestMapping(value = "/{id}/download", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource download(@PathVariable String id, HttpServletResponse response) {
        String outputFile = configRepository.get(id).getOutputFile();
        response.setHeader("Content-Disposition", String.format("attachment; filename=pricelist.xml"));
        return new FileSystemResource(new File(outputFile));
    }
}
