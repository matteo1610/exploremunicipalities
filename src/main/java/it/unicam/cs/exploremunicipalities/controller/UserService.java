package it.unicam.cs.exploremunicipalities.controller;

import it.unicam.cs.exploremunicipalities.model.user.License;
import it.unicam.cs.exploremunicipalities.model.user.User;

import java.util.Map;
import java.util.UUID;

/**
 * A service for managing users.
 */
public class UserService {
    private final RoleService roleService;
    private final Map<UUID, User> usersRepository;

    /**
     * Creates a new UserService with the given users' repository.
     * @param usersRepository the repository of users
     */
    public UserService(Map<UUID, User> usersRepository) {
        this.roleService = RoleService.getInstance();
        this.usersRepository = usersRepository;
    }

    /**
     * Returns the RoleService.
     * @return the RoleService
     */
    public RoleService getRoleService() {
        return this.roleService;
    }

    /**
     * Returns a user with the given id.
     * @param id the id of the user to get
     * @return a user with the given id
     */
    public User getUserById(UUID id) {
        return this.usersRepository.get(id);
    }

    /**
     * Adds a user to the repository.
     * @param user the user to add
     * @return true if the user was added, false otherwise
     */
    public boolean addUser(User user) {
        if(this.userExists(user.getEmail())) {
            return false;
        }
        this.usersRepository.put(user.getId(), user);
        return true;
    }

    private boolean userExists(String email) {
        return this.usersRepository.values().stream().anyMatch(user -> user.getEmail().equals(email));
    }

    /**
     * Removes a user from the repository.
     * @param user the user to remove
     * @return true if the user was removed, false otherwise
     */
    public boolean removeUser(User user) {
        if (this.usersRepository.containsKey(user.getId())) {
            this.usersRepository.remove(user.getId());
            return true;
        }
        return false;
    }

    /**
     * Sets a license for a user.
     * @param license the license to set
     * @return true if the license was set, false otherwise
     */
    public boolean setLicense(License license) {
        license.getUser().setEnabled(true);
        return this.roleService.setLicense(license);
    }
}
