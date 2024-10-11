package it.unicam.cs.exploremunicipalities.controller;

import it.unicam.cs.exploremunicipalities.config.JwtService;
import it.unicam.cs.exploremunicipalities.dto.entities.UserDTO;
import it.unicam.cs.exploremunicipalities.dto.request.*;
import it.unicam.cs.exploremunicipalities.dto.response.AuthenticateResponse;
import it.unicam.cs.exploremunicipalities.dto.response.RegisterResponse;
import it.unicam.cs.exploremunicipalities.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@RestController
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest request) {
        RegisterResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticateResponse> authenticateUser (@RequestBody AuthenticateRequest request) {
        AuthenticateResponse response = userService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<Set<UserDTO>> getUsers() {
        return ResponseEntity.ok(this.userService.getUsers());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/license")
    public ResponseEntity<Object> setLicense(SetLicenseRequest request) {
        try {
            this.userService.setLicense(request);
            return ResponseEntity.ok().body("License set successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/license/{userId}")
    public ResponseEntity<Object> removeLicense(@PathVariable long userId) {
        try {
            this.userService.removeLicense(userId);
            return ResponseEntity.ok().body("License removed successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/notifications")
    public ResponseEntity<Object> getNotifications() {
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        long userId = this.jwtService.extractUserId(token);
        try {
            return ResponseEntity.ok().body(this.userService.getNotifications(userId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/notifications/{userId}")
    public ResponseEntity<Object> sendNotification(@PathVariable long userId,
                                                   @RequestBody CreateNotificationRequest request) {
        try {
            this.userService.sendNotification(userId, request.message());
            return ResponseEntity.ok().body("Notification sent successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
