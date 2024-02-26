package it.unicam.cs.exploremunicipalities.controller;

import it.unicam.cs.exploremunicipalities.controller.dto.request.CreateLicenseRequest;
import it.unicam.cs.exploremunicipalities.controller.dto.request.CreateMunicipalityRequest;
import it.unicam.cs.exploremunicipalities.controller.dto.request.CreateUserRequest;
import it.unicam.cs.exploremunicipalities.controller.service.UserService;
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
}
