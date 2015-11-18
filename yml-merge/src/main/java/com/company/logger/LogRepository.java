package com.company.logger;

public interface LogRepository {

    void appendLogMessage(String id, String logMessage);

    String getLog(String id);

    void clean(String configId);

}
