package it.unicam.cs.exploremunicipalities.dto;

import it.unicam.cs.exploremunicipalities.model.user.UserRole;

public record LicenseDTO(UserRole role, long municipalityId) {
}
