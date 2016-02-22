package com.merger.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Yevhen on 2015-06-28.
 */

@RestController
@RequestMapping("/status")
public class AppController {

    private static final String STATUS_RUNNING = "Server is working";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String status() {
        return STATUS_RUNNING;
    }
}