package it.unicam.cs.exploremunicipalities.service;

import it.unicam.cs.exploremunicipalities.model.user.GlobalRole;
import it.unicam.cs.exploremunicipalities.service.repository.LicenseRepository;
import it.unicam.cs.exploremunicipalities.service.abstractions.RoleServiceInterface;
import it.unicam.cs.exploremunicipalities.model.user.License;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.user.MunicipalityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class RoleService {
    private final LicenseRepository licenseRepository;
    private final MunicipalityService municipalityService;

    public Set<MunicipalityRole> getRoles(long municipalityId) {
        return this.licenseRepository.findRolesByMunicipality(municipalityId);
    }

    public void setLicense(User user, long municipalityId, MunicipalityRole role) {
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

    private void createLicense(User user, long municipalityId, MunicipalityRole role) {
        License l = new License(role, this.municipalityService.getMunicipality(municipalityId));
        user.setLicense(l);
        user.setRole(GlobalRole.MUNICIPALITY_USER);
        this.licenseRepository.save(l);
    }

    private boolean hasCurator(long municipalityId) {
        return this.licenseRepository.findRolesByMunicipality(municipalityId).contains(MunicipalityRole.CURATOR);
    }

    private boolean hasAnimator(long municipalityId) {
        return this.licenseRepository.findRolesByMunicipality(municipalityId).contains(MunicipalityRole.ANIMATOR);
    }

    public void removeLicense(User user) {
        License l = user.getLicense();
        user.setLicense(null);
        user.setRole(GlobalRole.AUTHENTICATED_TOURIST);
        this.licenseRepository.delete(l);
    }
}
