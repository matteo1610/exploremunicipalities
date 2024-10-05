package it.unicam.cs.exploremunicipalities.dto.entities;

import it.unicam.cs.exploremunicipalities.model.user.License;
import it.unicam.cs.exploremunicipalities.model.user.MunicipalityRole;
import lombok.Getter;

@Getter
public class LicenseDTO {
    private final MunicipalityRole role;
    private final long municipalityId;

    public LicenseDTO(License license) {
        this.role = license.getRole();
        this.municipalityId = license.getMunicipality().getId();
    }
}
