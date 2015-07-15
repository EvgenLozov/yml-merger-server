package com.merger.controller;

import com.merger.Config;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Yevhen on 2015-07-14.
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Config get(@PathVariable("id") String id) {
        //todo get config from config repo
        return new Config();
    }

}
