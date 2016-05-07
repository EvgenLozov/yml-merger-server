package com.merger.controller;

import com.company.epoche.MergerEpocheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/prices")
public class MergerPricesController {

    @Autowired
    private MergerEpocheService mergerEpocheService;

    @RequestMapping(value = "/{currency}/{configId}", method = RequestMethod.GET)
    public FileSystemResource get(@PathVariable String currency, @PathVariable String configId,
                                  HttpServletResponse response) throws IOException {

        File file = mergerEpocheService.get(configId, currency);

        if (!file.exists()){
            response.setStatus(404);
            response.getWriter().write("File not found!");
            response.getWriter().flush();
            response.getWriter().close();
            return null;
        }

        response.setHeader("Content-Disposition", String.format("attachment; filename=%s-%s.xml", configId, currency));
        return new FileSystemResource(file);
    }

}
