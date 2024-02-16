package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.controller.repository.LicenseRepository;
import it.unicam.cs.exploremunicipalities.model.user.License;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.user.UserRole;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * A service for managing the roles of users in municipalities.
 */
@Service
public class RoleService {
    private final LicenseRepository licenseRepository;

    public RoleService(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    /**
     * Returns the role of a user in a municipality.
     * @param user The user whose role is to be returned.
     * @param municipality The municipality in which the user's role is to be returned.
     * @return The role of the user in the municipality.
     */
    public License getLicense(User user, Municipality municipality) {
        return this.licenseRepository.findUserRoleByUserIdAndMunicipalityId(user.getId(), municipality.getId());
    }

    /**
     * Returns the roles of a user in all the municipalities.
     * @param user The user whose roles are to be returned.
     * @return The roles of the user in all the municipalities.
     */
    public Set<Municipality> getMunicipalities(User user) {
        return this.licenseRepository.findMunicipalitiesByUserId(user.getId());
    }

    /**
     * Sets the role of a user in a municipality.
     * If the user already has a role in the municipality, the role is updated.
     * @param license The license to be set.
     */
    public void setLicense(License license) {
        // TODO: controllare se il ruolo  assegnato è "assegnabile"
        License l = this.getLicense(license.getUser(), license.getMunicipality());
        if (l == null) {
            this.licenseRepository.save(license);
        } else {
            l.setRole(license.getRole());
            this.licenseRepository.save(l);
        }
    }
}
