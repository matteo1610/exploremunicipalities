package it.unicam.cs.exploremunicipalities.model.content;

/**
 * This enum represents the state of a contest.
 */
public enum ContestState {
    CREATED,
    OPEN,
    CLOSED;

    /**
     * Returns the string representation of the contest state.
     *
     * @return the string representation of the contest state
     */
    public static ContestState fromString(String state) {
        return switch (state) {
            case "CREATED" -> CREATED;
            case "OPEN" -> OPEN;
            case "CLOSED" -> CLOSED;
            default -> throw new IllegalArgumentException("Invalid contest state");
        };
    }
}
