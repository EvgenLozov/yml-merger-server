package com.company.logger;

import java.util.HashMap;
import java.util.Map;

    public class InMemoryLogRepository implements LogRepository{

    Map<String, String> logs;

    public InMemoryLogRepository(Map<String, String> logs) {
        this.logs = logs;
    }

    @Override
    public void appendLogMessage(String id, String logMessage) {
        if (!logs.containsKey(id))
            logs.put(id, "");

        logs.put(id, logs.get(id) + logMessage);
    }

    @Override
    public String getLog(String id) {
        if (!logs.containsKey(id))
            return "Log is empty";

        return logs.get(id).toString();
    }

    public void clean(String configId)
    {
        logs.remove(configId);
    }
}
