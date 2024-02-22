package it.unicam.cs.exploremunicipalities.controller;

import it.unicam.cs.exploremunicipalities.controller.dto.request.UpdateUserRoleRequest;
import it.unicam.cs.exploremunicipalities.controller.dto.request.CreateUserRequest;
import it.unicam.cs.exploremunicipalities.controller.service.UserService;
import it.unicam.cs.exploremunicipalities.model.user.User;
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

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable long userId) {
        try {
            return ResponseEntity.ok().body(this.userService.getUser(userId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.ok().body(this.userService.getAllUsers());
    }

    @PostMapping()
    public ResponseEntity<Object> createUser(@RequestBody CreateUserRequest request) {
        try {
            this.userService.addUser(new User(request.email(), request.password()));
            return ResponseEntity.ok().body("User created successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable long userId) {
        try {
            this.userService.removeUser(userId);
            return ResponseEntity.ok().body("User deleted successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/license/{userId}")
    public ResponseEntity<Object> setLicense(@PathVariable long userId,
                                             @RequestBody UpdateUserRoleRequest request) {
        try {
            this.userService.setLicense(userId, request.municipalityId(), request.role());
            return ResponseEntity.ok().body("Role updated successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
