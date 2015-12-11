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

    LogRepository logRepository = new LogRepositoryProvider().get();

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
        logRepository.appendLogMessage(configId, dateFormat.format(new Date()) + " -- " + message + " <br>");
    }

    public String getConfigId() {
        return configId.get();
    }

    public void set(String configId){

        this.configId.set(configId);
    }

    public String getLog(String configId){
        return logRepository.getLog(configId);
    }

    public void clean(String configId)
    {
        logRepository.clean(configId);
    }
}
