package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.controller.repository.UserRepository;
import it.unicam.cs.exploremunicipalities.model.user.License;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.user.UserRole;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import org.springframework.stereotype.Service;

/**
 * A service for managing users.
 */
@Service
public class UserService {
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
     * @param municipality the municipality in which the user is to be added
     * @param role the role of the user in the municipality
     * @throws IllegalArgumentException if the user is already in the repository
     */
    public void addUser(User user, Municipality municipality, UserRole role) {
        if (this.userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("This email is already in use");
        }
        User newUser = this.userRepository.save(user);
        this.roleService.setLicense(new License(newUser, municipality, role));
    }

    /**
     * Removes a user from the repository.
     * @param userId the id of the user to remove
     * @throws IllegalArgumentException if the user does not exist
     */
    public void removeUser(long userId) {
        if (!this.userRepository.existsById(userId)) {
            throw new IllegalArgumentException("The user does not exist");
        }
        this.userRepository.delete(this.getUser(userId));
    }
}
