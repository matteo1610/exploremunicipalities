package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.controller.dto.UserDTO;
import it.unicam.cs.exploremunicipalities.controller.repository.UserRepository;
import it.unicam.cs.exploremunicipalities.controller.service.abstractions.UserServiceInterface;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.user.UserRole;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
     * Returns the details of all the users.
     * @return the details of all the users
     */
    public Set<UserDTO> getUsers() {
        Set<UserDTO> users = new HashSet<>();
        for (User u : this.userRepository.findAll()) {
            users.add(u.toDTO());
        }
        return users;
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
     * Adds a user to the repository.
     * @param user the user to add
     * @throws IllegalArgumentException if the user is already in the repository
     */
    public void createUser(User user) {
        if (this.userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("This email is already in use");
        }
        this.userRepository.save(new User(user.getEmail(), user.getPassword()));
    }

    /**
     * Removes a user from the repository. Also remove the license of the user.
     * @param userId the id of the user to remove
     * @throws IllegalArgumentException if the user does not exist
     */
    public void deleteUser(long userId) {
        this.userRepository.delete(this.getUser(userId));
    }

    /**
     * Set the license for a user in a municipality
     * @param userId id of user to set the license
     * @param municipalityId id of the municipality
     * @param role role of the user
     * @throws IllegalArgumentException if the municipality not exists
     * @throws IllegalArgumentException if the user not exists
     * @throws IllegalArgumentException if the user role not exists
     * @throws IllegalArgumentException if the municipality already has a curator or an animator
     */
    public void setLicense(long userId, long municipalityId, UserRole role) {
        this.roleService.setLicense(this.getUser(userId), municipalityId, role);
    }
}
