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

@SpringBootTest(args = "notifyFile=src/test/resources/request_input_invalid.json")
@ActiveProfiles("test")
@Import(CfgApp.class)
@ExtendWith(OutputCaptureExtension.class)
class EntrolyticsNotifierApplicationInputValidationFailTest {
    @Autowired
    private FileReader fileReader;


    @Test
    void testRunnerProcessesFile_validationFail(CapturedOutput output) throws Exception {
        // Arrange
        String testFilePath = "src/test/resources/request_payload.json";
        String data = fileReader.readFile(testFilePath);
        // Act – The app context loads and NotificationRunner runs automatically.
        String logs = output.getAll();

        // Assert expected application startup logs
        assertThat(logs)
                .contains("Starting EntrolyticsNotifierApplication")
                .contains("Started EntrolyticsNotifierApplication");

        // Assert NotificationRunner logs
        assertThat(logs)
                .contains("Starting notification job with file: " + testFilePath)
                .contains("Starting notification job...")
                .contains("Notification sent successfully.");

        // Assert ProcessMessage / sendNotification logs (if you use debug level)
        assertThat(logs)
                .contains("notificationContent is marked non-null but is null");

        // Verify that no unexpected ERROR messages appear
        assertThat(logs).doesNotContain("ERROR");

        // Optional: Print for manual inspection
        System.out.println("\n--- Captured Application Logs ---\n" + logs);

    }

}
