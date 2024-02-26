package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.controller.repository.LicenseRepository;
import it.unicam.cs.exploremunicipalities.controller.service.abstractions.RoleServiceInterface;
import it.unicam.cs.exploremunicipalities.model.user.License;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.user.UserRole;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleService implements RoleServiceInterface {
    private final LicenseRepository licenseRepository;
    private final MunicipalityService municipalityService;

    public RoleService(LicenseRepository licenseRepository, MunicipalityService municipalityService) {
        this.licenseRepository = licenseRepository;
        this.municipalityService = municipalityService;
    }

    /**
     * Get the roles associated in a municipality
     * @param municipalityId id of the municipality
     * @return the roles associated in a municipality
     */
    public Set<UserRole> getRoles(long municipalityId) {
        return this.licenseRepository.findRolesByMunicipality(municipalityId);
    }

    /**
     * Set the license for a user in a municipality
     * @param user user to set the license
     * @param municipalityId id of the municipality
     * @param role role of the user
     * @throws IllegalArgumentException if the municipality not exists
     * @throws IllegalArgumentException if the user not exists
     * @throws IllegalArgumentException if the user role not exists
     * @throws IllegalArgumentException if the municipality already has a curator or an animator
     */
    public void setLicense(User user, long municipalityId, UserRole role) {
        switch (role) {
            case CONTRIBUTOR, AUTHORIZED_CONTRIBUTOR -> this.createLicense(user, municipalityId, role);
            case CURATOR -> {
                if (this.hasCurator(municipalityId)) {
                    throw new IllegalArgumentException("Municipality already has a curator");
                }
                this.createLicense(user, municipalityId, role);
            }
            case ANIMATOR -> {
                if (this.hasAnimator(municipalityId)) {
                    throw new IllegalArgumentException("Municipality already has an animator");
                }
                this.createLicense(user, municipalityId, role);
            }
        }
    }

    private void createLicense(User user, long municipalityId, UserRole role) {
        License l = new License(role, this.municipalityService.getMunicipality(municipalityId));
        user.setLicense(l);
        this.licenseRepository.save(l);
    }

    private boolean hasCurator(long municipalityId) {
        return this.licenseRepository.findRolesByMunicipality(municipalityId).contains(UserRole.CURATOR);
    }

    private boolean hasAnimator(long municipalityId) {
        return this.licenseRepository.findRolesByMunicipality(municipalityId).contains(UserRole.ANIMATOR);
    }
}
