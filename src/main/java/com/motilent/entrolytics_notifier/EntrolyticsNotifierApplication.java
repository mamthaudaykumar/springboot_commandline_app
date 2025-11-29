package com.motilent.entrolytics_notifier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.motilent.entrolytics_notifier.config.CfgApp;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
@Import(CfgApp.class)
@RequiredArgsConstructor
public class EntrolyticsNotifierApplication {

    private static final Logger log = LoggerFactory.getLogger(EntrolyticsNotifierApplication.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        ConfigurableApplicationContext context = null;
        int exitCode = 0;

        try {
            context = SpringApplication.run(EntrolyticsNotifierApplication.class, args);

        } catch (Exception e) {
            exitCode = 1;
            logErrorAsJson(e);
        } finally {
            if (context != null) {
                int finalExitCode = exitCode;
                SpringApplication.exit(context, () -> finalExitCode);
            }
            System.exit(exitCode);
        }
    }

    private static void logErrorAsJson(Exception e) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("timestamp", Instant.now().toString());
        error.put("status", "ERROR");
        error.put("message", e.getMessage());
        error.put("exception", e.getClass().getName());
        if (e.getCause() != null) {
            error.put("cause", e.getCause().getMessage());
        }

        try {
            String json = mapper.writeValueAsString(error);
            System.err.println(json); // print to stderr for visibility in CI
        } catch (JsonProcessingException jsonEx) {
            log.error("Failed to serialize error JSON: {}", jsonEx.getMessage());
        }

        if (log.isDebugEnabled()) {
            log.debug("Full stack trace:", e);
        }
    }
}
