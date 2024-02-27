package it.unicam.cs.exploremunicipalities.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateLicenseRequest(
        @NotNull(message = "Municipality id cannot be null")
        long municipalityId,
        @NotNull(message = "Role cannot be null")
        String role
){
}
