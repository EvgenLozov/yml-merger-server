package com.company.logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public enum ProcessLogger {

    INSTANCE;
    private ThreadLocal<String> configId = new ThreadLocal<>();

    private Map<String, StringBuilder> logs = new HashMap<>();

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void info(String message){
        log(message);
        Logger.getAnonymousLogger().info(message);
    }

    public void warning(String message){
        log(message);
        Logger.getAnonymousLogger().warning(message);
    }

    private void log(String message){
        String configId = getConfigId();
        StringBuilder logBuilder = getLogString(configId);
        logBuilder.append(dateFormat.format(new Date()) + " -- " + message + " <br>");
    }

    private StringBuilder getLogString(String configId) {
        if (!logs.containsKey(configId))
            logs.put(configId, new StringBuilder());

        return logs.get(configId);
    }

    public String getConfigId() {
        return configId.get();
    }

    public void set(String configId){

        this.configId.set(configId);
    }

    public String getLog(String configId){
        if (!logs.containsKey(configId))
            return "Log is empty";

        return logs.get(configId).toString();
    }

    public void clean(String configId)
    {
        logs.remove(configId);
    }
}
