package it.unicam.cs.exploremunicipalities.model.user;

import it.unicam.cs.exploremunicipalities.model.util.Municipality;

/**
 * This class represents a license with a municipality and a role.
 */
public record License(UserRole role, Municipality municipality) {
}
