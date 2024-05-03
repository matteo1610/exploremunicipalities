package it.unicam.cs.exploremunicipalities.model.util;

/**
 * This class represents a geographical point with latitude and longitude coordinates.
 * The latitude and longitude values are in degrees.
 *
 * @param latitude The latitude of the point in degrees.
 * @param longitude The longitude of the point in degrees.
 * @throws IllegalArgumentException if the latitude is less than -90 or greater than 90, or if the longitude is less
 *                                  than -180 or greater than 180.
 */
public record Coordinate(double latitude, double longitude) {
    public Coordinate {
        if (latitude < -90 || latitude > 90)
            throw new IllegalArgumentException("Invalid latitude value: " + latitude);
        if (longitude < -180 || longitude > 180)
            throw new IllegalArgumentException("Invalid longitude value: " + longitude);
    }
}
