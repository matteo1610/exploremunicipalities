package it.unicam.cs.exploremunicipalities.model.user;

import it.unicam.cs.exploremunicipalities.model.util.Municipality;

import java.util.UUID;

/**
 * This class represents a license with a user, a municipality and a role.
 */
public class License {
    private final UUID id;
    private final User user;
    private final Municipality municipality;
    private UserRole role;

    /**
     * Creates a new license with the given user, municipality and role.
     * @param user the user of the license
     * @param municipality the municipality of the license
     * @param role the role of the license
     */
    public License(User user, Municipality municipality, UserRole role) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.municipality = municipality;
        this.role = role;
    }

    public UUID getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public Municipality getMunicipality() {
        return this.municipality;
    }

    public UserRole getRole() {
        return this.role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
