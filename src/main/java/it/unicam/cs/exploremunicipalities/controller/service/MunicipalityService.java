package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.controller.repository.ContributionRepository;
import it.unicam.cs.exploremunicipalities.controller.repository.MunicipalityRepository;
import it.unicam.cs.exploremunicipalities.controller.repository.PointRepository;
import it.unicam.cs.exploremunicipalities.model.content.contest.Contest;
import it.unicam.cs.exploremunicipalities.model.content.contribution.Contribution;
import it.unicam.cs.exploremunicipalities.model.content.Point;
import it.unicam.cs.exploremunicipalities.model.service.OSMService;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * This class represents a service for managing municipalities.
 */
@Service
public class MunicipalityService {
    private final Set<ContentService> contentServices;
    private final MunicipalityRepository municipalityRepository;
    private final PointRepository pointRepository;
    private final ContributionRepository contributionRepository;
    private final OSMService osmService;

    public MunicipalityService(MunicipalityRepository municipalityRepository, PointRepository pointRepository,
                               ContributionRepository contributionRepository) {
        this.contentServices = new HashSet<>();
        this.municipalityRepository = municipalityRepository;
        this.pointRepository = pointRepository;
        this.contributionRepository = contributionRepository;
        this.osmService = new OSMService();
    }

    /**
     * Returns a municipality with the given id.
     * @param id the id of the municipality to get
     * @return a municipality with the given id
     */
    public Municipality getMunicipalityById(UUID id) {
        return this.municipalityRepository.get(id);
    }

    /**
     * Returns all the municipalities.
     * @return all the municipalities
     */
    public Set<Municipality> getAllMunicipalities() {
        return new HashSet<>(this.municipalityRepository.values());
    }

    /**
     * Returns a content service for the given municipality.
     * @param municipality the municipality to get the content service for
     * @return a content service for the given municipality, or null if the municipality does not exist
     */
    public ContentService getContentService(Municipality municipality) {
        return this.contentServices.stream().filter(contentService -> contentService.getMunicipality()
                        .equals(municipality)).findFirst().orElse(null);
    }

    /**
     * Adds a municipality with the given name, province and identity point.
     * @param name the name of the municipality
     * @param province the province of the municipality
     * @return true if the municipality was added, false otherwise
     * @throws Exception if an error occurs while getting the coordinate point
     */
    public boolean addMunicipality(String name, String province) throws Exception {
        Municipality municipality = new Municipality(name, province, this.osmService.getCoordinatePointOfMunicipality(name));
        municipality = this.municipalityRepository.put(municipality.getId(), municipality);
        return this.contentServices.add(new ContentService(municipality, this.pointRepository, this.contributionRepository,
                this.contestRepository));
    }

    /**
     * Removes a municipality with the given id.
     * @param id the id of the municipality to remove
     * @return true if the municipality was removed, false otherwise
     */
    public boolean removeMunicipality(UUID id) {
        Municipality municipality = this.municipalityRepository.remove(id);
        ContentService contentService = this.getContentService(municipality);
        return this.contentServices.remove(contentService);
    }
}
