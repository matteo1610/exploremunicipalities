package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.controller.repository.ContributionRepository;
import it.unicam.cs.exploremunicipalities.controller.repository.MunicipalityRepository;
import it.unicam.cs.exploremunicipalities.controller.repository.PointRepository;
import it.unicam.cs.exploremunicipalities.model.service.OSMService;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import org.springframework.stereotype.Service;

/**
 * A service for managing municipalities.
 */
@Service
public class MunicipalityService {
    private final MunicipalityRepository municipalityRepository;
    private final ContributionService contributionService;
    private final OSMService osmService;

    public MunicipalityService(MunicipalityRepository municipalityRepository,
                               ContributionRepository contributionRepository, PointRepository pointRepository) {
        this.municipalityRepository = municipalityRepository;
        this.contributionService = new ContributionService(contributionRepository, pointRepository);
        this.osmService = new OSMService();
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
     * Returns all the municipalities.
     * @return all the municipalities
     */
    public Iterable<Municipality> getAllMunicipalities() {
        return this.municipalityRepository.findAll();
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
                .getCoordinatePointOfMunicipality(name));
        this.municipalityRepository.save(municipality);
    }

    /**
     * Removes a municipality with the given id. All the points of the municipality are removed.
     * @param municipalityId the id of the municipality to remove
     * @throws IllegalArgumentException if the municipality does not exist
     */
    public void removeMunicipality(long municipalityId) {
        this.contributionService.removeAllPointOfMunicipality(this.getMunicipality(municipalityId));
        this.municipalityRepository.deleteById(municipalityId);
    }
}
