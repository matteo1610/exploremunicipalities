package it.unicam.cs.exploremunicipalities.model.user;

/**
 * This enum represents the roles that a user can have.
 */
public enum UserRole {
    AUTHENTICATED_TOURIST,
    CONTRIBUTOR,
    AUTHORIZED_CONTRIBUTOR,
    CURATOR,
    ANIMATOR,
    PLATFORM_MANAGER;

    /**
     * This method returns the string representation of the role.
     * @param role the role
     * @return the string representation of the role
     */
    public UserRole fromString(String role) {
        return switch (role.toUpperCase()) {
            case "AUTHENTICATED_TOURIST" -> AUTHENTICATED_TOURIST;
            case "CONTRIBUTOR" -> CONTRIBUTOR;
            case "AUTHORIZED_CONTRIBUTOR" -> AUTHORIZED_CONTRIBUTOR;
            case "CURATOR" -> CURATOR;
            case "ANIMATOR" -> ANIMATOR;
            case "PLATFORM_MANAGER" -> PLATFORM_MANAGER;
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }
}
