package com.merger.controller;

import com.company.MergeService;
import com.company.config.Config;
import com.merger.ConfigRepository;
import company.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import java.io.*;

/**
 * @author lozov
 */
@RestController
@RequestMapping("/pricelists")
class PricelistController {

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    MergeService mergeService;

    @RequestMapping(value = "/{id}/merge", method = RequestMethod.POST)
    public void merge(@PathVariable final String id) throws FileNotFoundException, XMLStreamException {
        Runnable mergeTask = () -> {
            Config config = configRepository.get(id);
            try {
                mergeService.process(config);
            } catch (XMLStreamException | IOException e) {
                e.printStackTrace();
            }

            config.setLastMerge(System.currentTimeMillis());
            configRepository.save(config);
        };

        new Thread(mergeTask).start();
    }

    @RequestMapping(value = "/{id}/download", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource download(@PathVariable String id,
                                       @RequestParam Currency currency,
                                       HttpServletResponse response) throws IOException {

        String fileName = configRepository.get(id).getOutputFile() + File.separator + currency.name();
        File file = new File(fileName);

        if (!file.exists()){
            response.setStatus(404);
            response.getWriter().write("File not found!");
            response.getWriter().flush();
            response.getWriter().close();
            return null;
        }

        response.setHeader("Content-Disposition", String.format("attachment; filename=%s.xml", file.getName()));
        return new FileSystemResource(file);
    }
}
