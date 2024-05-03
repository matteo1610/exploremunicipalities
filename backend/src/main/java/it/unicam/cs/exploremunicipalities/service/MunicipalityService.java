package it.unicam.cs.exploremunicipalities.service;

import it.unicam.cs.exploremunicipalities.dto.MunicipalityDTO;
import it.unicam.cs.exploremunicipalities.service.repository.MunicipalityRepository;
import it.unicam.cs.exploremunicipalities.service.abstractions.MunicipalityServiceInterface;
import it.unicam.cs.exploremunicipalities.model.osm.OSMService;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * A service for managing municipalities.
 */
@Service
public class MunicipalityService implements MunicipalityServiceInterface {
    private final MunicipalityRepository municipalityRepository;
    private final OSMService osmService;

    public MunicipalityService(MunicipalityRepository municipalityRepository) {
        this.municipalityRepository = municipalityRepository;
        this.osmService = new OSMService();
    }

    @Override
    public Set<MunicipalityDTO> getMunicipalities() {
        Set<MunicipalityDTO> municipalities = new HashSet<>();
        for (Municipality m : this.municipalityRepository.findAll()) {
            municipalities.add(m.toDTO());
        }
        return municipalities;
    }

    @Override
    public Municipality getMunicipality(long municipalityId) {
        return this.municipalityRepository.findById(municipalityId).orElseThrow();
    }

    @Override
    public void createMunicipality(String name, String province) throws Exception {
        if (this.municipalityRepository.findByNameAndProvince(name, province) != null) {
            throw new IllegalArgumentException("Municipality already exists");
        }
        Municipality municipality = new Municipality(name, province, this.osmService
                .getCoordinatePointOfMunicipality(name, province));
        this.municipalityRepository.save(municipality);
    }

    @Override
    public void deleteMunicipality(long municipalityId) {
        this.municipalityRepository.delete(this.getMunicipality(municipalityId));
    }
}
