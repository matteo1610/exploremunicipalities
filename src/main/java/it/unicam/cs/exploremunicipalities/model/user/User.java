package it.unicam.cs.exploremunicipalities.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * This class represents a user with an email and a password.
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "AUTH_USER")
public class User {
    @Id
    @GeneratedValue
    private long id;
    @Setter
    private String email;
    @Setter
    private String password;
    @Setter
    private boolean isAdmin;
    @OneToMany(cascade = CascadeType.REMOVE)
    List<Notification> notifications;

    /**
     * Creates a new user with the given email and password.
     * @param email the email of the user
     * @param password the password of the user
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Adds a notification to the user.
     * @param notification the notification to add
     */
    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }

    /**
     * Removes a notification from the user.
     * @param notification the notification to remove
     */
    public void removeNotification(Notification notification) {
        this.notifications.remove(notification);
    }
}
