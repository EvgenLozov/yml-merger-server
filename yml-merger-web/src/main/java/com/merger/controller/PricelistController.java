package com.merger.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lozov
 */
@RestController
@RequestMapping("/pricelists")
class PricelistController {

    @RequestMapping(value = "/{id}/merge", method = RequestMethod.POST)
    public void merge(@PathVariable String id) {

    }

    @RequestMapping(value = "/{id}/download", method = RequestMethod.POST)
    public void download(@PathVariable String id) {

    }
}
