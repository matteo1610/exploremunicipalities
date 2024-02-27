package it.unicam.cs.exploremunicipalities.controller.dto;

import it.unicam.cs.exploremunicipalities.model.content.ContestState;
import it.unicam.cs.exploremunicipalities.model.util.Coordinate;

public record ContestDTO(
        long id,
        String title,
        String description,
        Coordinate position,
        String start,
        String end,
        ContestState state
) {
}
