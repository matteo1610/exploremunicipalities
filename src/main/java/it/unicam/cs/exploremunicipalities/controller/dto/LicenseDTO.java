package it.unicam.cs.exploremunicipalities.controller.dto;

import it.unicam.cs.exploremunicipalities.model.user.UserRole;

public record LicenseDTO(UserRole role, long municipalityId) {
}
