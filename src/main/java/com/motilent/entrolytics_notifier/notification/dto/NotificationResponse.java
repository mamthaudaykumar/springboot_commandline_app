package com.motilent.entrolytics_notifier.notification.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;


@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationResponse {
    private final String notificationUrl;
    private final NotificationContent notificationContent;
    private String responseData;
    private HttpStatus statusCode;
    private long responseTs;
}
