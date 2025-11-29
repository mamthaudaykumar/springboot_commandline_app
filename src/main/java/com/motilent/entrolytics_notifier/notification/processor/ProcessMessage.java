package com.motilent.entrolytics_notifier.notification.processor;

import com.motilent.entrolytics_notifier.notification.dto.NotificationResponse;

public interface ProcessMessage {
    NotificationResponse process(String content);
}
