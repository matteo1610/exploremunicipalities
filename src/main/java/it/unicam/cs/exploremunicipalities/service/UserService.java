package it.unicam.cs.exploremunicipalities.service;

import it.unicam.cs.exploremunicipalities.dto.ContributionDTO;
import it.unicam.cs.exploremunicipalities.dto.NotificationDTO;
import it.unicam.cs.exploremunicipalities.dto.UserDTO;
import it.unicam.cs.exploremunicipalities.model.content.contribution.Contribution;
import it.unicam.cs.exploremunicipalities.model.user.Notification;
import it.unicam.cs.exploremunicipalities.service.repository.UserRepository;
import it.unicam.cs.exploremunicipalities.service.abstractions.UserServiceInterface;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.user.UserRole;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A service for managing users.
 */
@Service
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final NotificationService notificationService;
    private final ContributionService contributionService;

    public UserService(UserRepository userRepository, RoleService roleService, NotificationService notificationService,
                       ContributionService contributionService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.notificationService = notificationService;
        this.contributionService = contributionService;
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
        User u = this.userRepository.save(new User(user.getEmail(), user.getPassword()));
        this.notificationService.createNotification(u, new Notification("Welcome to Explore Municipalities!"));
    }

    @Override
    public void deleteUser(long userId) {
        this.userRepository.delete(this.getUser(userId));
    }

    @Override
    public void setLicense(long userId, long municipalityId, UserRole role) {
        this.roleService.setLicense(this.getUser(userId), municipalityId, role);
    }

    @Override
    public void removeLicense(long userId) {
        this.roleService.removeLicense(this.getUser(userId));
    }

    @Override
    public Set<NotificationDTO> getNotifications(long userId) {
        return this.notificationService.getNotifications(this.getUser(userId));
    }

    @Override
    public void sendNotification(long userId, String message) {
        this.notificationService.createNotification(this.getUser(userId), new Notification(message));
    }

    @Override
    public Set<ContributionDTO> getFavorites(long userId) {
        return this.getUser(userId).getFavorites().stream().map(Contribution::toDTO).collect(Collectors.toSet());
    }

    @Override
    public void addFavorite(long userId, long contributionId) {
        this.getUser(userId).addFavorite(this.contributionService.getContribution(contributionId));
    }

    @Override
    public void removeFavorite(long userId, long contributionId) {
        Contribution c = this.getUser(userId).getFavorites().stream()
                .filter(contribution -> contribution.getId() == contributionId).findFirst().orElseThrow(
                        () -> new IllegalArgumentException("The contribution does not exist in the user's favorites"));
        this.getUser(userId).removeFavorite(c);
    }
}
