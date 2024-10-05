package it.unicam.cs.exploremunicipalities.service.abstractions;

import it.unicam.cs.exploremunicipalities.dto.entities.NotificationDTO;
import it.unicam.cs.exploremunicipalities.model.user.Notification;
import it.unicam.cs.exploremunicipalities.model.user.User;

import java.util.Set;

public interface NotificationServiceInterface {

    /**
     * Returns all the notifications of the user.
     * @param user the user to get the notifications from
     * @return all the notifications of the user
     */
    Set<NotificationDTO> getNotifications(User user);

    /**
     * Creates a new notification with the given message and adds it to the user.
     * @param user the user to add the notification to
     * @param notification the notification to be added to the user
     */
    void createNotification(User user, Notification notification);
}
