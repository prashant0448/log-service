package com.prashant.logservice.service;

import com.prashant.logservice.Controller.LogController;
import com.prashant.logservice.Instance.LoggerInstance;
import com.prashant.logservice.Validator.LogValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoggerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerService.class);

    @Autowired
    private LogValidator validator;

    @Autowired
    private LogController controller;

    public void execute(String... args) {
        LoggerInstance loggerInstance = LoggerInstance.getInstance();
        validator.validateInput(loggerInstance, args);
        controller.parseAndSaveEvents(loggerInstance);
    }
}
