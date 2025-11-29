package com.motilent.entrolytics_notifier;

import com.motilent.entrolytics_notifier.config.CfgApp;
import com.motilent.entrolytics_notifier.fileutility.FileReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(args = "notifyFile=src/test/resources/request_payload.json")
@ActiveProfiles("test")
@Import(CfgApp.class)
@ExtendWith(OutputCaptureExtension.class)
class EntrolyticsNotifierApplicationSuccessTest {

    @Test
    void testRunnerProcessesFileSuccessfully(CapturedOutput output) throws Exception {
        // Arrange
        String testFilePath = "src/test/resources/request_payload.json";
        // Act – The app context loads and NotificationRunner runs automatically.
        String logs = output.getAll();

        // Assert expected application startup logs
        assertThat(logs)
                .contains("Starting EntrolyticsNotifierApplication")
                .contains("Started EntrolyticsNotifierApplication");

        assertThat(logs)
                .contains("Starting notification job with file: " + testFilePath)
                .contains("Notification sent successfully.");


        // Assert ProcessMessage / sendNotification logs (if you use debug level)
        assertThat(logs)
                .contains("Notification URL:")
                .contains("HTTP Request Body:")
                .contains("HTTP Response Body:")
                .contains("HTTP Response Code:")
                .contains("Response Time:");


    }

}
