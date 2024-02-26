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

    @Override
    public Set<UserDTO> getUsers() {
        Set<UserDTO> users = new HashSet<>();
        for (User u : this.userRepository.findAll()) {
            users.add(u.toDTO());
        }
        return users;
    }

    @Override
    public User getUser(long userId) {
        return this.userRepository.findById(userId).orElseThrow();
    }

    @Override
    public void createUser(User user) {
        if (this.userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("This email is already in use");
        }
        this.userRepository.save(new User(user.getEmail(), user.getPassword()));
    }

    @Override
    public void deleteUser(long userId) {
        this.userRepository.delete(this.getUser(userId));
    }

    @Override
    public void setLicense(long userId, long municipalityId, UserRole role) {
        this.roleService.setLicense(this.getUser(userId), municipalityId, role);
    }
}
