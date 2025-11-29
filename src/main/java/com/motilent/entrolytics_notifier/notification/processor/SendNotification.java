package com.motilent.entrolytics_notifier.notification.processor;

import com.motilent.entrolytics_notifier.notification.dto.NotificationRequest;
import com.motilent.entrolytics_notifier.notification.dto.NotificationResponse;

public interface SendNotification {
    NotificationResponse send(NotificationRequest content);
}
