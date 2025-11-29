package com.motilent.entrolytics_notifier.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootExceptionReporter;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

public class GlobalExceptionReporter implements SpringBootExceptionReporter {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionReporter.class);

    public GlobalExceptionReporter(ConfigurableApplicationContext context) {
        // not used, but required by the interface
    }

    @Override
    public boolean reportException(Throwable failure) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("timestamp", Instant.now().toString());
        error.put("status", "ERROR");
        error.put("exception", failure.getClass().getSimpleName());
        error.put("message", failure.getMessage());
        if (failure.getCause() != null) {
            error.put("cause", failure.getCause().toString());
        }

        log.error("{}", error);
        return false; // allows SpringApplication to exit gracefully
    }
}
