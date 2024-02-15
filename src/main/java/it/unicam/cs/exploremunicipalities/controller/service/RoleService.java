package it.unicam.cs.exploremunicipalities.controller.service;

import it.unicam.cs.exploremunicipalities.model.user.License;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.user.UserRole;
import it.unicam.cs.exploremunicipalities.model.util.Municipality;

import java.util.*;
import java.util.stream.Collectors;

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
     * Returns a set of licenses for a given municipality.
     * @param municipality the municipality to get the licenses for
     * @return a list of licenses for the given municipality
     */
    public Set<License> getLicensesForMunicipality(Municipality municipality) {
        return this.licensesRepository.values().stream().filter(license -> license.getMunicipality()
                .equals(municipality)).collect(Collectors.toSet());
    }

    /**
     * Returns a set of municipalities for a given user and role.
     * @param user the user to get the municipalities for
     * @param role the role to get the municipalities for
     * @return a set of municipalities for the given user and role
     */
    public Set<Municipality> getMunicipalitiesForUser(User user, UserRole role) {
        return this.licensesRepository.values().stream().filter(license -> license.getUser()
                .equals(user) && license.getRole().equals(role)).map(License::getMunicipality)
                .collect(Collectors.toSet());
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
     * Sets a license for a given user, municipality and role.
     * @param user the user to set the license for
     * @param municipality the municipality to set the license for
     * @param role the role to set the license for
     */
    public void setLicense(User user, Municipality municipality, UserRole role) {
        License license = this.getLicense(user, municipality);
        if (license == null) {
            License newLicense = new License(user, municipality, role);
            this.licensesRepository.put(newLicense.getId(), newLicense);
        } else {
            license.setRole(role);
        }
    }
}
