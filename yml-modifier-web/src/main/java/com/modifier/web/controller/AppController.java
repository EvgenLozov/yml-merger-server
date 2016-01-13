package com.modifier.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Yevhen on 2015-06-28.
 */

@RestController
public class AppController {

    private static final String STATUS_RUNNING = "Server is working";

    @RequestMapping("/status")
    public String status() {
        return STATUS_RUNNING;
    }
}