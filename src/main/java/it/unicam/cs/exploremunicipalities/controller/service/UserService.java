package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.controller.repository.UserRepository;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.user.UserRole;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * A service for managing users.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final LicenseService licenseService;

    public UserService(UserRepository userRepository, LicenseService licenseService) {
        this.userRepository = userRepository;
        this.licenseService = licenseService;
    }

    /**
     * Returns a user by its id.
     * @param id the id of the user
     * @return the user with the given id
     */
    public Optional<User> getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public User addUser(String email, String password, ) {
        if(!this.userExists(email)) {
            User user = new User(email, password);
            this.userRepository.put(user.getId(), user);
            return true;
        }
        return false;
    }

    private boolean userExists(String email) {
        return this.userRepository.values().stream().anyMatch(user -> user.getEmail().equals(email));
    }

    /**
     * Removes a user from the repository.
     * @param id the id of the user to remove
     * @return true if the user was removed, false otherwise
     */
    public boolean removeUser(UUID id) {
        if (this.userRepository.containsKey(id)) {
            this.userRepository.remove(id);
            return true;
        }
        return false;
    }

    /**
     * Sets a license for a given user, municipality and role.
     * @param user the user to set the license for
     * @param municipality the municipality to set the license for
     * @param role the role to set the license for
     */
    public void setUserLicense(User user, Municipality municipality, UserRole role) {
        this.licenseService.setLicense(user, municipality, role);
    }
}
