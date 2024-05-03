package it.unicam.cs.exploremunicipalities.service.abstractions;

import it.unicam.cs.exploremunicipalities.dto.ContributionDTO;
import it.unicam.cs.exploremunicipalities.dto.NotificationDTO;
import it.unicam.cs.exploremunicipalities.dto.UserDTO;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.user.UserRole;

import java.util.Set;

public interface UserServiceInterface {

    /**
     * Returns the details of all the users.
     * @return the details of all the users
     */
    Set<UserDTO> getUsers();

    /**
     * Returns a user with the given id.
     * @param userId the id of the user to get
     * @return a user with the given id
     * @throws IllegalArgumentException if the user does not exist
     */
    User getUser(long userId);

    /**
     * Adds a user to the repository.
     * @param user the user to add
     * @throws IllegalArgumentException if the user is already in the repository
     */
    void createUser(User user);

    /**
     * Removes a user from the repository. Also remove the license of the user.
     * @param userId the id of the user to remove
     * @throws IllegalArgumentException if the user does not exist
     */
    void deleteUser(long userId);

    /**
     * Set the license for a user in a municipality
     * @param userId id of user to set the license
     * @param municipalityId id of the municipality
     * @param role role of the user
     * @throws IllegalArgumentException if the municipality doesn't exist
     * @throws IllegalArgumentException if the user doesn't exist
     * @throws IllegalArgumentException if the user role doesn't exist
     * @throws IllegalArgumentException if the municipality already has a curator or an animator
     */
    void setLicense(long userId, long municipalityId, UserRole role);

    /**
     * Removes the license of the user with the given id.
     * @param userId the id of the user
     */
    void removeLicense(long userId) throws IllegalArgumentException;

    /**
     * Returns all the notifications of the user.
     * @param userId the id of user to get the notifications from
     * @return all the notifications of the user
     */
    Set<NotificationDTO> getNotifications(long userId);

    /**
     * Creates a new notification with the given message and adds it to the user.
     * @param userId the id of user to add the notification to
     * @param message the message of the notification
     */
    void sendNotification(long userId, String message);

    /**
     * Returns all the contributions of the user.
     * @param userId the id of user to get the contributions from
     * @return all the contributions of the user
     */
    Set<ContributionDTO> getFavorites(long userId);

    /**
     * Adds a contribution to the user's list of favorites.
     * @param userId the id of user to add the favorite to
     * @param contributionId the id of the contribution to be added to the user
     */
    void addFavorite(long userId, long contributionId);

    /**
     * Removes a contribution from the user's list of favorites.
     * @param userId the id of user to remove the favorite from
     * @param contributionId the id of the contribution to be removed from the user
     * @throws IllegalArgumentException if the contribution does not exist in the user's favorites
     */
    void removeFavorite(long userId, long contributionId);
}
