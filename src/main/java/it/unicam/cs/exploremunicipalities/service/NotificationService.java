package it.unicam.cs.exploremunicipalities.service;

import it.unicam.cs.exploremunicipalities.dto.NotificationDTO;
import it.unicam.cs.exploremunicipalities.model.user.Notification;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.service.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Returns all the notifications of the user.
     * @param user the user to get the notifications from
     * @return all the notifications of the user
     */
    public Set<NotificationDTO> getNotifications(User user) {
        Set<NotificationDTO> notifications = new HashSet<>();
        for (Notification n : user.getNotifications()) {
            notifications.add(n.toDTO());
        }
        return notifications;
    }

    /**
     * Creates a new notification with the given message and adds it to the user.
     * @param user the user to add the notification to
     * @param notification the notification to be added to the user
     */
    public void createNotification(User user, Notification notification) {
        user.addNotification(notification);
        this.notificationRepository.save(notification);
    }
}
