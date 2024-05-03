package it.unicam.cs.exploremunicipalities.model.content.contribution;

/**
 * This enum represents the state of a contribution.
 */
public enum ContributionState {
    PENDING,
    APPROVED,
    INTO_CONTEST;

    /**
     * This method returns the string representation of the state.
     * @param state the state
     * @return the string representation of the state
     */
    public static ContributionState fromString(String state) {
        return switch (state.toUpperCase()) {
            case "PENDING" -> PENDING;
            case "APPROVED" -> APPROVED;
            case "INTO_CONTEST" -> INTO_CONTEST;
            default -> throw new IllegalArgumentException("Invalid state: " + state);
        };
    }
}
