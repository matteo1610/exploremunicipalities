package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.controller.repository.LicenseRepository;
import it.unicam.cs.exploremunicipalities.controller.service.abstractions.MunicipalityServiceInterface;
import it.unicam.cs.exploremunicipalities.controller.service.abstractions.RoleServiceInterface;
import it.unicam.cs.exploremunicipalities.model.user.License;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import it.unicam.cs.exploremunicipalities.model.user.UserRole;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * A service for managing the roles of users in municipalities.
 */
@Service
public class RoleService implements RoleServiceInterface {
    private final LicenseRepository licenseRepository;
    private final MunicipalityService municipalityService;

    public RoleService(LicenseRepository licenseRepository, MunicipalityService municipalityService) {
        this.licenseRepository = licenseRepository;
        this.municipalityService = municipalityService;
    }

    /**
     * Returns the role of a user in a municipality.
     * @param user The user whose role is to be returned.
     * @param municipalityId The ID of the municipality in which the user's role is to be returned.
     * @return The role of the user in the municipality.
     */
    public License getLicense(User user, long municipalityId) {
        return this.licenseRepository.findByUserIdAndMunicipalityId(user.getId(), municipalityId);
    }

    /**
     * Returns the roles of a user in all the municipalities.
     * @param user The user whose roles are to be returned.
     * @return The roles of the user in all the municipalities.
     */
    public Set<License> getLicenses(User user) {
        return this.licenseRepository.findByUserId(user.getId());
    }

    /**
     * Sets the role of a user in a municipality.
     * If the user already has a role in the municipality, the role is updated. Otherwise, a new license is created.
     * @param user The user whose role is to be set.
     * @param municipalityId The ID of the municipality in which the user's role is to be set.
     * @param role The role to be set.
     * @throws IllegalArgumentException if the municipality does not exist.
     */
    public void setLicense(User user, long municipalityId, String role) {
        // TODO: controllare se il ruolo  assegnato è "assegnabile"
        Municipality m = this.municipalityService.getMunicipality(municipalityId);
        License l = this.getLicense(user, municipalityId);
        if (l == null) {
            this.licenseRepository.save(new License(user, m, UserRole.valueOf(role)));
        } else {
            l.setRole(UserRole.valueOf(role));
            this.licenseRepository.save(l);
        }
    }

    /**
     * Removes all licenses associated with a user.
     * @param user The user whose licenses are to be removed.
     */
    public void removeLicenses(User user) {
        this.licenseRepository.deleteAll(this.getLicenses(user));
    }
}
