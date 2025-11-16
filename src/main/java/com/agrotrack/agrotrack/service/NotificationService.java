package com.agrotrack.agrotrack.service;

import com.agrotrack.agrotrack.entity.Notification;
import com.agrotrack.agrotrack.entity.Plant;
import com.agrotrack.agrotrack.entity.User;
import com.agrotrack.agrotrack.repository.NotificationRepository;
import com.agrotrack.agrotrack.repository.PlantRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final PlantRepository plantRepository; // Ajout de PlantRepository

    public NotificationService(NotificationRepository notificationRepository,
                               UserService userService, PlantRepository plantRepository) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
        this.plantRepository = plantRepository; // Injection de PlantRepository
    }

    public List<Notification> getUserNotifications() {
        Long userId = userService.getCurrentUserId();
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getUserUnreadNotifications() {
        Long userId = userService.getCurrentUserId();
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }

    public Notification createNotification(Notification notification, Long plantId) {
        Long userId = userService.getCurrentUserId();
        Optional<User> user = userService.getCurrentUser();

        if (user.isPresent()) {
            notification.setUser(user.get());
            notification.setCreatedAt(LocalDateTime.now());

            if (plantId != null) {
                Optional<Plant> plant = plantRepository.findById(plantId);
                if (plant.isPresent() && plant.get().getUser().getId() == userId) { // Utilise == pour les primitifs
                    notification.setPlant(plant.get());
                }
            }

            if (notification.getNotificationDate() == null) {
                notification.setNotificationDate(LocalDateTime.now());
            }

            // Assure un type par défaut si non fourni
            if (notification.getNotificationType() == null) {
                notification.setNotificationType("RAPPEL");
            }

            return notificationRepository.save(notification);
        }
        throw new RuntimeException("Utilisateur non trouvé");
    }
    public Notification markAsRead(Long notificationId) {
        Long userId = userService.getCurrentUserId();
        Optional<Notification> notification = notificationRepository.findByIdAndUserId(notificationId, userId);

        if (notification.isPresent()) {
            Notification existingNotification = notification.get();
            existingNotification.setIsRead(true);
            return notificationRepository.save(existingNotification);
        }
        throw new RuntimeException("Notification non trouvée");
    }

    public void deleteNotification(Long notificationId) {
        Long userId = userService.getCurrentUserId();
        if (notificationRepository.existsByIdAndUserId(notificationId, userId)) {
            notificationRepository.deleteById(notificationId);
        } else {
            throw new RuntimeException("Notification non trouvée");
        }
    }

    public long getUnreadNotificationsCount() {
        Long userId = userService.getCurrentUserId();
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }

    public List<Notification> getPlantNotifications(Long plantId) {
        Long userId = userService.getCurrentUserId();
        // Vérifier que la plante appartient à l'utilisateur
        Optional<Plant> plant = plantRepository.findByIdAndUserId(plantId, userId);
        if (plant.isPresent()) {
            return notificationRepository.findByPlantId(plantId);
        }
        throw new RuntimeException("Plante non trouvée");
    }
}