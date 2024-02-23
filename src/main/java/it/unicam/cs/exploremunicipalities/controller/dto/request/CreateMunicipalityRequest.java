package it.unicam.cs.exploremunicipalities.controller.dto.request;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for the Municipality entity.
 */
public record CreateMunicipalityRequest(
        @NotNull(message = "Name cannot be null")
        String name,
        @NotNull(message = "Province cannot be null")
        String province
) {
}
