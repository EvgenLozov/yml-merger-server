package com.merger.controller;

import com.merger.Config;
import com.merger.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Yevhen on 2015-07-14.
 */
@RestController
@RequestMapping("/configs")
public class ConfigController {

    @Autowired
    private ConfigRepository configRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Config> list() {
        return configRepository.list();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Config get(@PathVariable String id) {
        return configRepository.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Config create(@RequestBody Config config) {
        return configRepository.create(config);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Config save(@PathVariable String id, @RequestBody Config config) {
        configRepository.save(config);
        return config;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) {
        configRepository.delete(id);
    }
}
