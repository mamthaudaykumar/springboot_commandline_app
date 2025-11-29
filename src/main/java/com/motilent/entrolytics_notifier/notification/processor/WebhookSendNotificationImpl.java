package com.motilent.entrolytics_notifier.notification.processor;

import com.motilent.entrolytics_notifier.notification.dto.NotificationContent;
import com.motilent.entrolytics_notifier.notification.dto.NotificationRequest;
import com.motilent.entrolytics_notifier.notification.dto.NotificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;

@Component
public class WebhookSendNotificationImpl implements SendNotification{


    private final ObjectMapper objectMapper;

    private final WebClient webClient;

    @Autowired
    public WebhookSendNotificationImpl(ObjectMapper objectMapper, WebClient webClient) {

        this.objectMapper = objectMapper;
        this.webClient = webClient;
    }

    @Override
    public NotificationResponse send(NotificationRequest content) {
        long start = System.currentTimeMillis();

        var responseEntity = webClient.post()
                .uri(content.getNotificationUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(content)
                .retrieve()
                .toEntity(String.class) // gives both body and status
                .timeout(Duration.ofSeconds(10))
                .retryWhen(
                        Retry.backoff(3, Duration.ofSeconds(2))
                                .filter(ex -> ex instanceof WebClientResponseException &&
                                        ((WebClientResponseException) ex).getStatusCode().is5xxServerError())
                                .onRetryExhaustedThrow((spec, signal) -> signal.failure())
                )
                .block();

        long duration = System.currentTimeMillis() - start;

        return new NotificationResponse(
                content.getNotificationUrl(),
                objectMapper.convertValue(content.getNotificationContent(), NotificationContent.class),
                responseEntity.getBody(),
                (HttpStatus) responseEntity.getStatusCode(),
                duration
        );
    }

}
