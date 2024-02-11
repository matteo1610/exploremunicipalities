package it.unicam.cs.exploremunicipalities.controller;

import it.unicam.cs.exploremunicipalities.model.user.License;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.util.Municipality;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A service for managing the roles of users in municipalities. This class is a singleton.
 */
public class RoleService {
    private final Map<UUID, License> licensesRepository;
    private static RoleService instance;

    /**
     * The constructor is private to prevent instantiation from outside the class.
     */
    private RoleService() {
        this.licensesRepository = new HashMap<>();
    }

    /**
     * Returns the instance of the RoleService.
     * @return the instance of the RoleService
     */
    public static RoleService getInstance() {
        if (instance == null) {
            instance = new RoleService();
        }
        return instance;
    }

    /**
     * Returns a list of licenses for a given municipality.
     * @param municipality the municipality to get the licenses for
     * @return a list of licenses for the given municipality
     */
    public List<License> getLicenses(Municipality municipality) {
        return this.licensesRepository.values().stream().filter(license -> license.getMunicipality()
                .equals(municipality)).toList();
    }

    /**
     * Returns a license for a given user and municipality.
     * @param user the user to get the license for
     * @param municipality the municipality to get the license for
     * @return a license for the given user and municipality, or null if it does not exist
     */
    public License getLicense(User user, Municipality municipality) {
        return this.licensesRepository.values().stream().filter(license -> license.getUser()
                .equals(user) && license.getMunicipality().equals(municipality)).findFirst().orElse(null);
    }

    /**
     * Sets a license for a user in a municipality.
     * @param license the license to set
     * @return true if the license was set, false otherwise
     */
    public boolean setLicense(License license) {
        if (this.getLicense(license.getUser(), license.getMunicipality()) != null) {
            return false;
        }
        this.licensesRepository.put(license.getId(), license);
        return true;
    }

    /**
     * Updates a license for a user in a municipality.
     * @param license the license to update
     * @return true if the license was updated, false otherwise
     */
    public boolean updateLicense(License license) {
        if (this.getLicense(license.getUser(), license.getMunicipality()) == null) {
            return false;
        }
        this.licensesRepository.put(license.getId(), license);
        return true;
    }
}
