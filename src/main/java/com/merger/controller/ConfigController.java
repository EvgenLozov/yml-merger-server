package com.merger.controller;

import com.merger.Config;
import com.merger.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Yevhen on 2015-07-14.
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private ConfigRepository configRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Config get() {
        return configRepository.get();
    }

}
