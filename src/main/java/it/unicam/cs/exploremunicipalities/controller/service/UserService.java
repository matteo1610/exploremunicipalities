package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.controller.repository.UserRepository;
import it.unicam.cs.exploremunicipalities.model.user.License;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.user.UserRole;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * A service for managing users.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final MunicipalityService municipalityService;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, MunicipalityService municipalityService, RoleService roleService) {
        this.userRepository = userRepository;
        this.municipalityService = municipalityService;
        this.roleService = roleService;
    }

    /**
     * Returns a user from the repository.
     * @param user the user to return
     * @return the user
     * @throws IllegalArgumentException if the user is not in the repository
     */
    public User getUser(User user) {
        return this.userRepository.findById(user.getId()).orElseThrow();
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
     * @throws IllegalArgumentException if the municipality does not exist
     */
    public void addUser(User user, Municipality municipality, UserRole role) {
        if (this.userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("This email is already in use");
        }
        if (this.municipalityService.getMunicipality(municipality.getId()) == null) {
            throw new IllegalArgumentException("The municipality does not exist");
        }
        User newUser = this.userRepository.save(user);
        this.roleService.setLicense(new License(newUser, municipality, role));
    }

    /**
     * Removes a user from the repository.
     * @param user the user to remove
     * @throws IllegalArgumentException if the user is not in the repository
     */
    public void removeUser(User user) {
        if (!this.userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("The user does not exist");
        }
        this.userRepository.delete(this.getUser(user));
    }
}
