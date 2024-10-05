package it.unicam.cs.exploremunicipalities.service;

import it.unicam.cs.exploremunicipalities.config.JwtService;
import it.unicam.cs.exploremunicipalities.dto.entities.ContributionDTO;
import it.unicam.cs.exploremunicipalities.dto.entities.NotificationDTO;
import it.unicam.cs.exploremunicipalities.dto.entities.UserDTO;
import it.unicam.cs.exploremunicipalities.dto.request.AuthenticateRequest;
import it.unicam.cs.exploremunicipalities.dto.request.RegisterRequest;
import it.unicam.cs.exploremunicipalities.dto.request.SetLicenseRequest;
import it.unicam.cs.exploremunicipalities.dto.response.AuthenticateResponse;
import it.unicam.cs.exploremunicipalities.dto.response.RegisterResponse;
import it.unicam.cs.exploremunicipalities.model.content.contribution.Contribution;
import it.unicam.cs.exploremunicipalities.model.user.Notification;
import it.unicam.cs.exploremunicipalities.service.repository.UserRepository;
import it.unicam.cs.exploremunicipalities.service.abstractions.UserServiceInterface;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.user.MunicipalityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final RoleService roleService;
    private final NotificationService notificationService;
    private final ContributionService contributionService;

    public RegisterResponse register(RegisterRequest request) {
        this.userRepository.findByEmail(request.email()).ifPresent(user -> {
            throw new RuntimeException("This email is already in use");
        });
        var user = new User(request.email(), this.passwordEncoder.encode(request.password()));
        this.userRepository.save(user);
        return new RegisterResponse(new UserDTO(user));
    }

    public AuthenticateResponse authenticate(AuthenticateRequest request) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(),
                request.password()));
        var user = this.userRepository.findByEmail(request.email()).orElseThrow(() -> new UsernameNotFoundException(
                "User not found"));
        var jwtToken = this.jwtService.generateToken(user, user.getId());
        return new AuthenticateResponse(jwtToken);
    }

    public Set<UserDTO> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(UserDTO::new)
                .collect(Collectors.toSet());
    }

    public User getUser(long userId) {
        return this.userRepository.findById(userId).orElseThrow();
    }

    public void setLicense(SetLicenseRequest request) {
        this.roleService.setLicense(this.getUser(request.userId()), request.municipalityId(), MunicipalityRole.valueOf(
                request.role()));
    }

    public void removeLicense(long userId) {
        this.roleService.removeLicense(this.getUser(userId));
    }

    public Set<NotificationDTO> getNotifications(long userId) {
        return this.notificationService.getNotifications(this.getUser(userId));
    }

    public void sendNotification(long userId, String message) {
        this.notificationService.createNotification(this.getUser(userId), new Notification(message));
    }

    public Set<ContributionDTO> getFavorites(long userId) {
        return this.getUser(userId).getFavorites().stream().map(Contribution::toDTO).collect(Collectors.toSet());
    }

    public void addFavorite(long userId, long contributionId) {
        this.getUser(userId).addFavorite(this.contributionService.getContribution(contributionId));
    }

    public void removeFavorite(long userId, long contributionId) {
        Contribution c = this.getUser(userId).getFavorites().stream()
                .filter(contribution -> contribution.getId() == contributionId).findFirst().orElseThrow(
                        () -> new IllegalArgumentException("The contribution does not exist in the user's favorites"));
        this.getUser(userId).removeFavorite(c);
    }
}
