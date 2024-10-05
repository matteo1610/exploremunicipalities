package it.unicam.cs.exploremunicipalities.service;

import it.unicam.cs.exploremunicipalities.dto.entities.NotificationDTO;
import it.unicam.cs.exploremunicipalities.model.user.Notification;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.service.abstractions.NotificationServiceInterface;
import it.unicam.cs.exploremunicipalities.service.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class NotificationService implements NotificationServiceInterface {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Set<NotificationDTO> getNotifications(User user) {
        Set<NotificationDTO> notifications = new HashSet<>();
        for (Notification n : user.getNotifications()) {
            notifications.add(n.toDTO());
        }
        return notifications;
    }

    @Override
    public void createNotification(User user, Notification notification) {
        user.addNotification(notification);
        this.notificationRepository.save(notification);
    }
}
