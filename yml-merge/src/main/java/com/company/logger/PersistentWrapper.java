package com.company.logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

public class PersistentWrapper implements LogRepository {

    Map<String, String> logs;
    LogRepository logRepository;
    String logsFile;
    ObjectMapper objectMapper = new ObjectMapper();

    public PersistentWrapper(Map<String, String> logs, LogRepository logRepository, String logsFile) {
        this.logs = logs;
        this.logRepository = logRepository;
        this.logsFile = logsFile;
    }

    @Override
    public void appendLogMessage(String id, String logMessage) {
        logRepository.appendLogMessage(id, logMessage);
        persist();
    }

    @Override
    public String getLog(String id) {
        return logRepository.getLog(id);
    }

    @Override
    public void clean(String configId) {
        logRepository.clean(configId);
        persist();
    }

    private void persist()
    {
        try {
            objectMapper.writeValue(new File(logsFile), logs);
        } catch (IOException e) {
            Logger.getAnonymousLogger().warning("Unable to persist logs to file "+logsFile+". Cause: "+e.getMessage());
        }
    }
}
