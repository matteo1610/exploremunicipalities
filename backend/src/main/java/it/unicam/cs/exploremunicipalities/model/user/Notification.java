package it.unicam.cs.exploremunicipalities.model.user;

import it.unicam.cs.exploremunicipalities.dto.NotificationDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Notification {
    @Id
    @GeneratedValue
    private long id;
    @Setter
    private String message;
    private LocalDateTime timeStamp;

    /**
     * Creates a new notification with the given message and the current time.
     * @param message the message of the notification
     * @throws IllegalArgumentException if the message is blank
     */
    public Notification(String message) {
        if (message.isBlank()) {
            throw new IllegalArgumentException("The notification message cannot be blank.");
        }
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }

    /**
     * Converts the notification to a DTO.
     * @return the DTO of the notification
     */
    public NotificationDTO toDTO() {
        return new NotificationDTO(this.message, this.timeStamp.toString());
    }
}
