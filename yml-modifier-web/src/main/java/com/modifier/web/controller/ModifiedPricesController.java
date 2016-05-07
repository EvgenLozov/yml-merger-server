package com.modifier.web.controller;

import com.company.ModifierEpocheService;
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
public class ModifiedPricesController {

    @Autowired
    private ModifierEpocheService modifierEpocheService;

    @RequestMapping(value = "/{configId}", method = RequestMethod.GET)
    public FileSystemResource get(@PathVariable String configId, HttpServletResponse response) throws IOException {

        File file = modifierEpocheService.get(configId);

        if (!file.exists()){
            response.setStatus(404);
            response.getWriter().write("File not found!");
            response.getWriter().flush();
            response.getWriter().close();
            return null;
        }

        response.setHeader("Content-Disposition", String.format("attachment; filename=%s.xml", configId));
        return new FileSystemResource(file);
    }
}
