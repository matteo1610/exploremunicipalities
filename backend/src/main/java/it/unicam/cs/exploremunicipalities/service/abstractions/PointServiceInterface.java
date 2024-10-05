package it.unicam.cs.exploremunicipalities.service.abstractions;

import it.unicam.cs.exploremunicipalities.dto.entities.PointDTO;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import it.unicam.cs.exploremunicipalities.model.content.Point;
import it.unicam.cs.exploremunicipalities.model.util.Coordinate;

import java.util.Set;

public interface PointServiceInterface {

    /**
     * Returns the details of the points associated to the municipality with the given id.
     *
     * @return the details of the points associated to the municipality with the given id
     */
    Set<PointDTO> getPoints(long municipalityId);

    /**
     * Returns a point with the given id.
     * @param pointId the id of the point to get
     * @return a point with the given id
     * @throws IllegalArgumentException if the point does not exist
     */
    Point getPoint(long pointId);

    /**
     * Associates a point with the given position to the municipality.
     * @param position the position of the point
     * @param municipality the municipality to associate to the point
     * @return the associated point
     * @throws IllegalArgumentException if the municipality does not exist, or if the point is not in the municipality
     * @throws Exception if an error occurs while getting information of the position from the OSM service
     */
    Point associatePoint(Coordinate position, Municipality municipality) throws Exception;

    /**
     * Deletes a point with the given id.
     * @param pointId the id of the point to delete
     */
    void deletePoint(long pointId);

}
