package com.motilent.entrolytics_notifier.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {

    @NonNull
    private String notificationUrl;

    @NonNull
    private NotificationContent notificationContent;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NotificationContent {
        @NonNull
        private String reportUID;
        @NonNull
        private String studyInstanceUID;
    }
}
