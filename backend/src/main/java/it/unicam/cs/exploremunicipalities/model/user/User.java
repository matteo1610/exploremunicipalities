package it.unicam.cs.exploremunicipalities.model.user;

import it.unicam.cs.exploremunicipalities.dto.UserDTO;
import it.unicam.cs.exploremunicipalities.model.content.contribution.Contribution;
import it.unicam.cs.exploremunicipalities.model.content.contribution.ContributionState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @OneToOne(cascade = CascadeType.REMOVE)
    private License license;
    @OneToMany(cascade = CascadeType.REMOVE)
    private Set<Notification> notifications;
    @OneToMany
    private Set<Contribution> favorites;

    /**
     * Creates a new user with the given email and password.
     * @param email the email of the user
     * @param password the password of the user
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.notifications = new HashSet<>();
    }

    /**
     * Converts the user to a DTO.
     * @return the DTO of the user
     */
    public UserDTO toDTO() {
        return new UserDTO(this.id, this.email, this.password, this.license != null ? license.toDTO() : null);
    }

    /**
     * Adds a notification to the user's list of notifications.
     * @param notification the notification to be added to the user
     */
    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }

    /**
     * Adds a contribution to the user's list of favorites.
     * @param contribution the contribution to be added to the user
     * @throws IllegalArgumentException if the contribution is not approved
     */
    public void addFavorite(Contribution contribution) {
        if (contribution.getState() != ContributionState.APPROVED) {
            throw new IllegalArgumentException("You can save only approved contributions as favorites.");
        }
        this.favorites.add(contribution);
    }

    /**
     * Removes a contribution from the user's list of favorites.
     * @param contribution the contribution to be removed from the user
     */
    public void removeFavorite(Contribution contribution) {
        this.favorites.remove(contribution);
    }
}
