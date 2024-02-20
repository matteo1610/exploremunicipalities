package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.controller.repository.UserRepository;
import it.unicam.cs.exploremunicipalities.controller.service.abstractions.RoleServiceInterface;
import it.unicam.cs.exploremunicipalities.controller.service.abstractions.UserServiceInterface;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.user.UserRole;
import org.springframework.stereotype.Service;

/**
 * A service for managing users.
 */
@Service
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    /**
     * Returns a user with the given id.
     * @param userId the id of the user to get
     * @return a user with the given id
     * @throws IllegalArgumentException if the user does not exist
     */
    public User getUser(long userId) {
        return this.userRepository.findById(userId).orElseThrow();
    }

    /**
     * Returns all the users in the repository.
     * @return all the users in the repository
     */
    public Iterable<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    /**
     * Adds a user to the repository.
     * @param user the user to add
     * @throws IllegalArgumentException if the user is already in the repository
     * @throws IllegalArgumentException if the municipality does not exist
     */
    public void addUser(User user) {
        if (this.userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("This email is already in use");
        }
        this.userRepository.save(new User(user.getEmail(), user.getPassword()));
    }

    /**
     * Removes a user from the repository. Also removes all the licenses of the user.
     * @param userId the id of the user to remove
     * @throws IllegalArgumentException if the user does not exist
     */
    public void removeUser(long userId) {
        User user = this.getUser(userId);
        this.roleService.removeLicenses(user);
        this.userRepository.delete(user);
    }

    /**
     * Set the role of a user in a municipality.
     * @param userId the id of the user
     * @param municipalityId the id of the municipality
     * @param newRole the new role of the user
     * @throws IllegalArgumentException if the user does not exist
     * @throws IllegalArgumentException if the municipality does not exist
     * @throws IllegalArgumentException if the role does not exist
     */
    public void setLicense(long userId, long municipalityId, String newRole) {
        if (this.getUser(userId) == null) {
            throw new IllegalArgumentException("The user does not exist");
        }
        this.roleService.setLicense(this.getUser(userId), municipalityId, newRole);
    }
}
