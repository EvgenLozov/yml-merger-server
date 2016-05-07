package com.merger.controller;

import com.company.config.ConfigGroup;
import company.config.ConfigRepository;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Naya on 04.04.2016.
 */
@RestController
@RequestMapping("/configsGroup")
public class ConfigGroupController {

    @Autowired
    private ConfigRepository<ConfigGroup> configGroupRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<ConfigGroup> list(){
        return configGroupRepository.list();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ConfigGroup get(@PathVariable String id){
        return configGroupRepository.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ConfigGroup create(@RequestBody ConfigGroup configGroup){
        return configGroupRepository.create(configGroup);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ConfigGroup save(@PathVariable String id, @RequestBody ConfigGroup configGroup){
        configGroupRepository.save(configGroup);
        return configGroupRepository.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id) throws SchedulerException {
        configGroupRepository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
