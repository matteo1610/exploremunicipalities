package it.unicam.cs.exploremunicipalities.controller.service.abstractions;

import it.unicam.cs.exploremunicipalities.controller.dto.UserDTO;
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
}
