package it.unicam.cs.exploremunicipalities.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateUserRoleRequest(
        @NotNull(message = "Municipality id cannot be null")
        long municipalityId,
        @NotNull(message = "Role ole cannot be null")
        String role
) {
}
