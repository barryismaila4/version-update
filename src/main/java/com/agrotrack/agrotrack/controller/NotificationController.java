package com.agrotrack.agrotrack.controller;

import com.agrotrack.agrotrack.entity.Notification;
import com.agrotrack.agrotrack.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getUserNotifications() {
        List<Notification> notifications = notificationService.getUserNotifications();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/unread")
    public ResponseEntity<List<Notification>> getUserUnreadNotifications() {
        List<Notification> notifications = notificationService.getUserUnreadNotifications();
        return ResponseEntity.ok(notifications);
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(
            @RequestBody Notification notification,
            @RequestParam(required = false) Long plantId) {
        try {
            Notification createdNotification = notificationService.createNotification(notification, plantId);
            return ResponseEntity.ok(createdNotification);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
        try {
            Notification notification = notificationService.markAsRead(id);
            return ResponseEntity.ok(notification);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        try {
            notificationService.deleteNotification(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count/unread")
    public ResponseEntity<Long> getUnreadNotificationsCount() {
        long count = notificationService.getUnreadNotificationsCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/plant/{plantId}")
    public ResponseEntity<List<Notification>> getPlantNotifications(@PathVariable Long plantId) {
        try {
            List<Notification> notifications = notificationService.getPlantNotifications(plantId);
            return ResponseEntity.ok(notifications);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}