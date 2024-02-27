package it.unicam.cs.exploremunicipalities.controller.dto.request;

import it.unicam.cs.exploremunicipalities.model.util.Coordinate;

public record CreateContestRequest(
        String title,
        String description,
        Coordinate position
) {
}
