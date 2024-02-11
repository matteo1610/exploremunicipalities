package it.unicam.cs.exploremunicipalities.model.user;

import java.util.Arrays;

/**
 * This enum represents the roles that a user can have.
 */
public enum UserRole {
    AUTHENTICATED_TOURIST("Authenticated Tourist"),
    CONTRIBUTOR("Contributor"),
    AUTHORIZED_CONTRIBUTOR("Authorized Contributor"),
    CURATOR("Curator"),
    ANIMATOR("Animator"),
    PLATFORM_MANAGER("Platform Manager");

    public final String role;

    UserRole(String role) {
        this.role = role;
    }

    /**
     * This method returns the role with the given name.
     *
     * @param role the name of the role
     * @return the role with the given name
     */
    public static UserRole getRole(String role) {
        return Arrays.stream(UserRole.values())
                .filter(userRole -> userRole.role.equals(role))
                .findFirst()
                .orElse(null);
    }
}
