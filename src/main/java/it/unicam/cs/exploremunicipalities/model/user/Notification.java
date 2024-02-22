package it.unicam.cs.exploremunicipalities.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
     */
    public Notification(String message) {
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }
}
