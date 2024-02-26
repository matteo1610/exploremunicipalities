package it.unicam.cs.exploremunicipalities.controller.service.abstractions;

import it.unicam.cs.exploremunicipalities.controller.dto.MunicipalityDTO;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;

import java.util.Set;

public interface MunicipalityServiceInterface {

    /**
     * Returns the details of all the municipalities.
     * @return the details of all the municipalities
     */
    Set<MunicipalityDTO> getMunicipalities();

    /**
     * Returns a municipality with the given id.
     * @param municipalityId the id of the municipality to get
     * @return a municipality with the given id
     * @throws IllegalArgumentException if the municipality does not exist
     */
    Municipality getMunicipality(long municipalityId);

    /**
     * Adds a municipality with the given name, province and identity point.
     * @param name the name of the municipality
     * @param province the province of the municipality
     * @throws IllegalArgumentException if the municipality already exists
     * @throws Exception if the identity point of the municipality cannot be found
     */
    void createMunicipality(String name, String province) throws Exception;

    /**
     * Removes a municipality with the given id. All the points of the municipality are removed.
     * @param municipalityId the id of the municipality to remove
     * @throws IllegalArgumentException if the municipality does not exist
     */
    void deleteMunicipality(long municipalityId);
}
