package com.motilent.entrolytics_notifier.notification.processor;

import lombok.extern.slf4j.Slf4j;
import com.motilent.entrolytics_notifier.notification.dto.NotificationRequest;
import com.motilent.entrolytics_notifier.notification.dto.NotificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
public class ProcessMessageImpl implements ProcessMessage{

    private final ObjectMapper objectMapper;

    private final WebhookSendNotificationImpl sendNotification;

    @Autowired
    public ProcessMessageImpl(ObjectMapper objectMapper, WebhookSendNotificationImpl sendNotification) {

        this.objectMapper = objectMapper;
        this.sendNotification = sendNotification;
    }

    @Override
    public NotificationResponse process(String content) {
        NotificationRequest notificationRequest = objectMapper.convertValue(objectMapper.readTree(content), NotificationRequest.class);
        NotificationResponse response = sendNotification.send(notificationRequest);
        log.debug("\nNotification URL: " + notificationRequest.getNotificationUrl());
        log.debug("\nHTTP Request Body:\n" + notificationRequest.getNotificationContent());
        log.debug("\nHTTP Response Body:\n" + response);
        log.debug("\nHTTP Response Code: " + response.getStatusCode());
        log.debug("Response Time: " + response.getResponseTs() + " ms");
        return response;
    }
}
