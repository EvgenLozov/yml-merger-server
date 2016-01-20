package com.modifier.web.controller;

import com.company.ModifierConfig;
import company.config.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Naya on 13.01.2016.
 */
@RestController
@RequestMapping("/configs")
public class ConfigController {

    @Autowired
    private ConfigRepository<ModifierConfig> configRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<ModifierConfig> list() {
        return configRepository.list();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModifierConfig get(@PathVariable String id) {
        return configRepository.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModifierConfig create(@RequestBody ModifierConfig config)   {
        ModifierConfig newConfig = configRepository.create(config);
        return config;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ModifierConfig save(@PathVariable String id, @RequestBody ModifierConfig config)  {
        configRepository.save(config);
        return config;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id)  {
        configRepository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
