package com.merger.controller;

import com.company.logger.ProcessLogger;
import com.company.service.MergeService;
import com.company.config.MergerConfig;
import company.Currency;
import company.config.ConfigRepository;
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
    private ConfigRepository<MergerConfig> configRepository;

    @Autowired
    MergeService mergeService;

    @RequestMapping(value = "/{id}/merge", method = RequestMethod.POST)
    public void merge(@PathVariable final String id) throws FileNotFoundException, XMLStreamException {
        Runnable mergeTask = () -> {
            MergerConfig config = configRepository.get(id);

            ProcessLogger.INSTANCE.set(config.getId());

            try {
                mergeService.process(config);
            } catch (XMLStreamException | IOException e) {
                e.printStackTrace();
            }
        };

        new Thread(mergeTask).start();
    }

    @RequestMapping(value = "/{id}/download", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource download(@PathVariable String id,
                                       @RequestParam Currency currency,
                                       HttpServletResponse response) throws IOException {

        String fileName = configRepository.get(id).getOutputFile() + File.separator + currency.getFileName();
        File file = new File(fileName);

        if (!file.exists()){
            response.setStatus(404);
            response.getWriter().write("File not found!");
            response.getWriter().flush();
            response.getWriter().close();
            return null;
        }

        response.setHeader("Content-Disposition", String.format("attachment; filename=%s", file.getName()));
        return new FileSystemResource(file);
    }
}
