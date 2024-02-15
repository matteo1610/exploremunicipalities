package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.user.UserRole;
import it.unicam.cs.exploremunicipalities.model.util.Municipality;

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
     * @param email the email of the user
     * @param password the password of the user
     * @return true if the user was added, false otherwise
     */
    public boolean addUser(String email, String password) {
        if(!this.userExists(email)) {
            User user = new User(email, password);
            this.usersRepository.put(user.getId(), user);
            return true;
        }
        return false;
    }

    private boolean userExists(String email) {
        return this.usersRepository.values().stream().anyMatch(user -> user.getEmail().equals(email));
    }

    /**
     * Removes a user from the repository.
     * @param id the id of the user to remove
     * @return true if the user was removed, false otherwise
     */
    public boolean removeUser(UUID id) {
        if (this.usersRepository.containsKey(id)) {
            this.usersRepository.remove(id);
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
        this.roleService.setLicense(user, municipality, role);
    }
}
