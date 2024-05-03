package it.unicam.cs.exploremunicipalities.controller;

import it.unicam.cs.exploremunicipalities.dto.request.CreateLicenseRequest;
import it.unicam.cs.exploremunicipalities.dto.request.CreateNotificationRequest;
import it.unicam.cs.exploremunicipalities.dto.request.CreateUserRequest;
import it.unicam.cs.exploremunicipalities.service.NotificationService;
import it.unicam.cs.exploremunicipalities.service.UserService;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUsers")
    public ResponseEntity<Object> getUsers() {
        return ResponseEntity.ok().body(this.userService.getUsers());
    }

    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser(@RequestBody CreateUserRequest request) {
        try {
            this.userService.createUser(new User(request.email(), request.password()));
            return ResponseEntity.ok().body("User created successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("deleteUser/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable long userId) {
        try {
            this.userService.deleteUser(userId);
            return ResponseEntity.ok().body("User deleted successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/setLicense/{userId}")
    public ResponseEntity<Object> setLicense(@PathVariable long userId, @RequestBody CreateLicenseRequest request) {
        try {
            this.userService.setLicense(userId, request.municipalityId(), UserRole.valueOf(request.role()));
            return ResponseEntity.ok().body("License set successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/removeLicense/{userId}")
    public ResponseEntity<Object> removeLicense(@PathVariable long userId) {
        try {
            this.userService.removeLicense(userId);
            return ResponseEntity.ok().body("License removed successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/getNotifications/{userId}")
    public ResponseEntity<Object> getNotifications(@PathVariable long userId) {
        try {
            return ResponseEntity.ok().body(this.userService.getNotifications(userId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/sendNotification/{userId}")
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
