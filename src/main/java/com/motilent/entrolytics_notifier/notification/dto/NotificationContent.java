package com.motilent.entrolytics_notifier.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationContent {
    private String reportUID;
    private String studyInstanceUID;
}
