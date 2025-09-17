// 代码生成时间: 2025-09-18 06:56:29
package com.example.notification;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import io.smallrye.reactive.messaging.annotations.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    @Inject
    @ConfigProperty(name = "notification.max.count")
    int maxNotificationCount;

    @Inject
    @Channel("notifications")
    Emitter<String> notificationEmitter;

    @Inject
    @Connector("notification-sink")
    org.eclipse.microprofile.reactive.messaging.Incoming<String> notificationIncoming;

    @PostConstruct
    void init() {
        LOGGER.info("Notification Service initialized with max count: {}", maxNotificationCount);
    }

    /**
     * Send a notification message to the channel.
     * 
     * @param message The notification message to be sent.
     */
    public void sendNotification(String message) {
        if(message == null || message.trim().isEmpty()) {
            LOGGER.error("Attempted to send empty notification message");
            throw new IllegalArgumentException("Notification message cannot be empty");
        }
        notificationEmitter.send(message);
    }

    /**
     * Receive incoming notification messages and process them.
     * 
     * @param message The incoming notification message.
     */
    @Incoming("notifications")
    public void receiveNotification(String message) {
        if(message == null || message.trim().isEmpty()) {
            LOGGER.error("Received empty notification message");
            return;
        }
        LOGGER.info("Received notification: {}", message);
        // Process the notification message (e.g., store to database, send to external system, etc.)
    }

    /**
     * Shutdown hook to stop the notification service gracefully.
     */
    public void stop() {
        LOGGER.info("Notification Service is stopping...");
        // Add any cleanup code here, if necessary
    }
}
