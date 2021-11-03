package com.prashant.logservice;


import com.prashant.logservice.service.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.time.Instant;

@SpringBootApplication
public class LogServiceApplication implements CommandLineRunner {

	@Autowired
	LoggerService loggerService;

	private static final Logger logger= LoggerFactory.getLogger(LogServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LogServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Instant start = Instant.now();
		loggerService.execute(args);
		Instant end = Instant.now();
		logger.info("Total time: {}ms", Duration.between(start, end).toMillis());
	}
}
