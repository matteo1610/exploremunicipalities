package it.unicam.cs.exploremunicipalities.service;

import it.unicam.cs.exploremunicipalities.service.repository.LicenseRepository;
import it.unicam.cs.exploremunicipalities.service.abstractions.RoleServiceInterface;
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

    @Override
    public License getLicense(long licenseId) {
        return this.licenseRepository.findById(licenseId).orElseThrow(() -> new IllegalArgumentException(
                "The license does not exist"));
    }

    @Override
    public Set<UserRole> getRoles(long municipalityId) {
        return this.licenseRepository.findRolesByMunicipality(municipalityId);
    }

    @Override
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

    @Override
    public void removeLicense(User user) {
        License l = user.getLicense();
        user.setLicense(null);
        this.licenseRepository.delete(l);
    }
}
