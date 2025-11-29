package com.motilent.entrolytics_notifier.notification;

import com.motilent.entrolytics_notifier.EntrolyticsNotifierApplication;
import com.motilent.entrolytics_notifier.fileutility.FileReader;
import com.motilent.entrolytics_notifier.notification.processor.ProcessMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NotificationRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(NotificationRunner.class);
    private final FileReader fileReader;
    private final ProcessMessage processMessage;

    @Autowired
    public NotificationRunner(FileReader fileReader, ProcessMessage processMessage) {
        this.fileReader = fileReader;
        this.processMessage = processMessage;
    }

    @Override
    public void run(String... args) throws IOException {
        try {
            String jsonPath = null;

            if (args != null) {
                for (String arg : args) {
                    if (arg.startsWith("notifyFile=")) {
                        jsonPath = arg.substring("notifyFile=".length());
                        break;
                    }
                }
            }

            if (jsonPath == null || jsonPath.isBlank()) {
                throw new IllegalArgumentException("JSON file path argument is required. Use notifyFile=path/to/file.json");
            }

            log.info("Starting notification job with file: {}", jsonPath);
            String fileContent = fileReader.readFile(jsonPath);
            processMessage.process(fileContent);
            log.info("Notification sent successfully.");

        } catch (Exception e) {
            log.info("Notification send failure.");
            EntrolyticsNotifierApplication.logErrorAsJson(e);
        }}
}
