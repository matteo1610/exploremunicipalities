package it.unicam.cs.exploremunicipalities.controller.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotNull(message = "Email cannot be null")
        @Email(message = "Email should be valid")
        String email,
        @NotNull(message = "Password cannot be null")
        @Size(min = 6, max = 20, message = "Password should be between 6 and 20 characters long")
        String password
) {
}
