package com.prashant.logservice.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prashant.logservice.Instance.LoggerInstance;
import com.prashant.logservice.Repository.AlertRepository;
import com.prashant.logservice.model.Entity.Alert;
import com.prashant.logservice.model.Event;
import com.prashant.logservice.model.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Component
public class LogController {

    @Autowired
    private AlertRepository alertRepository;

    @Value("${config.alert.threshold.value}")
    private String thresholdTime;

    @Value("${config.max.rows.count}")
    private String maxRowsCount;

    private static final Logger logger= LoggerFactory.getLogger(LogController.class);

    public void parseAndSaveEvents(LoggerInstance loggerInstance) {

        Map<String, Event> eventMap = new HashMap<>();
        Map<String, Alert> alertMap = new HashMap<>();
        String line = null;
        logger.info("Event parsing and saving the alerts in database...");
        try (LineIterator li = FileUtils.lineIterator(new ClassPathResource("files/" + loggerInstance.getLogFilePath()).getFile())) {
            while (li.hasNext()) {
                Event event;
                try {
                    event = new ObjectMapper().readValue(li.nextLine(), Event.class);
                    logger.trace("{}", event);
                    if (eventMap.containsKey(event.getId())) {
                        Event e1 = eventMap.get(event.getId());
                        long executionTime = computeEventExecutionTime(event, e1);
                        Alert alert = new Alert(event, Math.toIntExact(executionTime));
                        if (executionTime > Integer.valueOf(thresholdTime)) {
                            alert.setAlert(Boolean.TRUE);
                            logger.trace("Execution time for the event {} is {} ms", event.getId(), executionTime);
                        }
                        alertMap.put(event.getId(), alert);
                        eventMap.remove(event.getId());
                    } else {
                        eventMap.put(event.getId(), event);
                    }
                } catch (JsonProcessingException e) {
                    logger.error("Sorry finding difficulty in parsing the event {}", e.getMessage());
                }
                if (alertMap.size() > Integer.valueOf(maxRowsCount)) {
                    logger.debug("Saving alert into the database");
                    alertRepository.saveAll(alertMap.values());
                    alertMap = new HashMap<>();
                }
            }
            if (alertMap.size() != 0) {
                logger.debug("Saving alert into the database");
                alertRepository.saveAll(alertMap.values());
            }
        } catch (IOException e) {
            logger.error("Sorry, not able to access the file: {}", e.getMessage());
        }
    }

    private long computeEventExecutionTime(Event event1, Event event2) {
        Event startEvent = Stream.of(event1, event2).filter(p -> State.STARTED.equals(p.getState())).findFirst().orElse(null);
        Event endEvent = Stream.of(event1, event2).filter(p -> State.FINISHED.equals(p.getState())).findFirst().orElse(null);

        return Objects.requireNonNull(endEvent).getTimestamp() - Objects.requireNonNull(startEvent).getTimestamp();
    }
}
