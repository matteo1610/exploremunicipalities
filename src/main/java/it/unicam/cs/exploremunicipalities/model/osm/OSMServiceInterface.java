package it.unicam.cs.exploremunicipalities.model.osm;

import it.unicam.cs.exploremunicipalities.model.util.Coordinate;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;

/**
 * This interface represents the service that provides the operations to interact with the OpenStreetMap API.
 */
public interface OSMServiceInterface {
    /**
     * Returns true if the given point is in the municipality with the given name.
     * @param point the point
     * @param municipality the municipality
     * @return true if the point is in the municipality, false otherwise
     * @throws Exception if an error occurs while getting the response
     */
    boolean isPointInMunicipality(Coordinate point, Municipality municipality) throws Exception;
}
