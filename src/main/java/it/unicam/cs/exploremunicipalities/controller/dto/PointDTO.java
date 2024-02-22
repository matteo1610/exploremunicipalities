package it.unicam.cs.exploremunicipalities.controller.dto;

import it.unicam.cs.exploremunicipalities.model.util.CoordinatePoint;

/**
 * A DTO for a point.
 */
public record PointDTO(long id, CoordinatePoint position) {
}
