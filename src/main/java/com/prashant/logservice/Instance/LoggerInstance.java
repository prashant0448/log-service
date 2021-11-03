package com.prashant.logservice.Instance;

public class LoggerInstance {

    private static LoggerInstance loggerInstance;

    private String logFilePath;

    private LoggerInstance(){
    }

    public static LoggerInstance getInstance() {
        if (loggerInstance == null)
            loggerInstance = new LoggerInstance();
        return loggerInstance;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    @Override
    public String toString() {
        return "Logger{" +
                "logFilePath='" + logFilePath + '\'' +
                '}';
    }
}
