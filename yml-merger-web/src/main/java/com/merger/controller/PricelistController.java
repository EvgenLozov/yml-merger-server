package com.merger.controller;

import com.company.MergeService;
import com.merger.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

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
    public void merge(@PathVariable String id) throws FileNotFoundException, XMLStreamException {
        mergeService.process(configRepository.get(id));
    }

    @RequestMapping(value = "/{id}/download", method = RequestMethod.POST)
    public void download(@PathVariable String id) {

    }
}
