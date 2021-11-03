package com.prashant.logservice.Validator;

import com.prashant.logservice.Instance.LoggerInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Component
public class LogValidator {
    private static final Logger logger = LoggerFactory.getLogger(LogValidator.class);

    public void validateInput(LoggerInstance loggerInstance, String[] args) {

        logger.info("Input validation in progress...");
        if(args.length >=1)
            validateFilePath(loggerInstance,args[0]);
        else
            throw new IllegalArgumentException("Invalid file path");
    }

    private void validateFilePath(LoggerInstance loggerInstance, String logFilePath) {
        logger.info("Log File Path: {}", logFilePath);
        loggerInstance.setLogFilePath(logFilePath);

        try {
            File file = new ClassPathResource("files/" + logFilePath).getFile();
            if (!file.exists()) {
                file = new ClassPathResource(logFilePath).getFile();
                if (!file.exists()) {
                    file = new File(logFilePath);
                }
            }

            if (!file.exists())
                throw new FileNotFoundException("Not able to read the file " + logFilePath);
        } catch (IOException e) {
            logger.error("Sorry, We cannot find any file at {} ", logFilePath);
        }
    }


}
