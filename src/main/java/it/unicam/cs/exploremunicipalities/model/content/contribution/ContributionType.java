package it.unicam.cs.exploremunicipalities.model.content.contribution;


/**
 * This enum represents the type of contribution that can be made by a user.
 */
public enum ContributionType {
    POINT_OF_INTEREST,
    EVENT,
    ITINERARY;

    /**
     * Returns the string representation of the contribution type.
     * @return the string representation of the contribution type
     */
    public static ContributionType fromString(String type) {
        return switch (type) {
            case "POINT_OF_INTEREST" -> POINT_OF_INTEREST;
            case "EVENT" -> EVENT;
            case "ITINERARY" -> ITINERARY;
            default -> throw new IllegalArgumentException("Invalid contribution type");
        };
    }
}
