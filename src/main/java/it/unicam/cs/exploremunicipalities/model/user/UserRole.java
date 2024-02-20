package it.unicam.cs.exploremunicipalities.model.user;

/**
 * This enum represents the roles that a user can have.
 */
public enum UserRole {
    CONTRIBUTOR,
    AUTHORIZED_CONTRIBUTOR,
    CURATOR,
    ANIMATOR;

    /**
     * This method returns the string representation of the role.
     * @param role the role
     * @return the string representation of the role
     */
    public UserRole fromString(String role) {
        return switch (role.toUpperCase()) {
            case "CONTRIBUTOR" -> CONTRIBUTOR;
            case "AUTHORIZED_CONTRIBUTOR" -> AUTHORIZED_CONTRIBUTOR;
            case "CURATOR" -> CURATOR;
            case "ANIMATOR" -> ANIMATOR;
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }
}
