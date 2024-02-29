package it.unicam.cs.exploremunicipalities.service.abstractions;

import it.unicam.cs.exploremunicipalities.model.user.License;
import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.user.UserRole;

import java.util.Set;

public interface RoleServiceInterface {
    /**
     * Returns the license with the given id.
     * @param licenseId the id of the license to get
     * @return the license with the given id
     */
    License getLicense(long licenseId);

    /**
     * Get the roles associated in a municipality
     * @param municipalityId id of the municipality
     * @return the roles associated in a municipality
     */
    Set<UserRole> getRoles(long municipalityId);

    /**
     * Set the license for a user in a municipality
     * @param user user to set the license
     * @param municipalityId id of the municipality
     * @param role role of the user
     * @throws IllegalArgumentException if the municipality not exists
     * @throws IllegalArgumentException if the user not exists
     * @throws IllegalArgumentException if the user role not exists
     * @throws IllegalArgumentException if the municipality already has a curator or an animator
     */
    void setLicense(User user, long municipalityId, UserRole role);

    /**
     * Removes the license from the user.
     * @param user the user to remove the license from
     */
    void removeLicense(User user);
}
