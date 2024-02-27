package it.unicam.cs.exploremunicipalities.dto.request;

import it.unicam.cs.exploremunicipalities.model.util.Coordinate;

public record CreateContestRequest(
        String title,
        String description,
        Coordinate position
) {
}
