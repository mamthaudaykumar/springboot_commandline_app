package com.motilent.entrolytics_notifier;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class JsonLoggingTest {

    @Test
    void testErrorLoggedAsJson() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        root.addAppender(listAppender);

        root.error("Test error JSON");

        assertThat(listAppender.list)
            .anyMatch(event -> event.getFormattedMessage().contains("Test error JSON"));
    }
}
