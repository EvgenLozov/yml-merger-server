package com.company.logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class LogRepositoryProvider implements Supplier<LogRepository>{

    String logFileName = "logs/application-logs.json";

    @Override
    public LogRepository get() {
        new File(logFileName).getParentFile().mkdirs();

        Map<String,String> logs = getLogs();
        return new PersistentWrapper(logs, new InMemoryLogRepository(logs), logFileName);
    }

    private Map<String,String> getLogs()
    {
        File logFile = new File(logFileName);
        if (logFile.exists())
            try {
                return new ObjectMapper().readValue(logFile, Map.class);
            } catch (IOException e) {
                Logger.getAnonymousLogger().warning("Unable to read logs from log file: "+logFileName+". Cause: "+e.getMessage());
            }

        return new HashMap<>();

    }
}
