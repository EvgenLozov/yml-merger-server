package com.company.service;

import com.company.config.MergerConfig;
import com.company.logger.ProcessLogger;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yevhen
 */
public class SingleProcessMergeService implements MergeService {
    private static final ProcessLogger logger = ProcessLogger.INSTANCE;

    private Map<String, Boolean> proceses = new HashMap<>();

    private MergeService mergeService;

    public SingleProcessMergeService(MergeService mergeService) {
        this.mergeService = mergeService;
    }

    @Override
    public void process(MergerConfig config) throws IOException, XMLStreamException {
        if (proceses.containsKey(config.getId()) && proceses.get(config.getId())){
            logger.info("Process for config " + config.getName() + "is already  running.");
            return;
        }

        putProcess(config.getId(), true);

        try {
            ProcessLogger.INSTANCE.clean(config.getId());
            logger.info("Process for config " + config.getName() + " is started");
            mergeService.process(config);
            logger.info("Process for config " + config.getName() + " is finished successfully");
        } finally {
            logger.info("Process for config " + config.getName() + " is stopped");
            putProcess(config.getId(), false);
        }
    }

    private synchronized void putProcess(String configId, boolean value){
        proceses.put(configId, value);
    }
}
