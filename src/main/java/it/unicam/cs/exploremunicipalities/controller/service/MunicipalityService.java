package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.controller.dto.MunicipalityDTO;
import it.unicam.cs.exploremunicipalities.controller.repository.MunicipalityRepository;
import it.unicam.cs.exploremunicipalities.controller.service.abstractions.MunicipalityServiceInterface;
import it.unicam.cs.exploremunicipalities.model.service.OSMService;
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

    /**
     * Returns the details of all the municipalities.
     * @return the details of all the municipalities
     */
    public Set<MunicipalityDTO> getMunicipalities() {
        Set<MunicipalityDTO> municipalities = new HashSet<>();
        for (Municipality m : this.municipalityRepository.findAll()) {
            municipalities.add(m.toDTO());
        }
        return municipalities;
    }

    /**
     * Returns a municipality with the given id.
     * @param id the id of the municipality to get
     * @return a municipality with the given id
     * @throws IllegalArgumentException if the municipality does not exist
     */
    public Municipality getMunicipality(long id) {
        return this.municipalityRepository.findById(id).orElseThrow();
    }











    /**
     * Adds a municipality with the given name, province and identity point.
     * @param name the name of the municipality
     * @param province the province of the municipality
     * @throws IllegalArgumentException if the municipality already exists
     * @throws Exception if the identity point of the municipality cannot be found
     */
    public void addMunicipality(String name, String province) throws Exception {
        if (this.municipalityRepository.findByName(name) != null) {
            throw new IllegalArgumentException("Municipality already exists");
        }
        Municipality municipality = new Municipality(name, province, this.osmService
                .getCoordinatePointOfMunicipality(name, province));
        this.municipalityRepository.save(municipality);
    }

    /**
     * Removes a municipality with the given id. All the points of the municipality are removed.
     * @param municipalityId the id of the municipality to remove
     * @throws IllegalArgumentException if the municipality does not exist
     */
    public void removeMunicipality(long municipalityId) {
        this.municipalityRepository.deleteById(municipalityId);
    }
}
