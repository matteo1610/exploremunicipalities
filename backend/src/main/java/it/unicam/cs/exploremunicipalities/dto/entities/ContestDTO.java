package it.unicam.cs.exploremunicipalities.dto.entities;

import it.unicam.cs.exploremunicipalities.model.content.ContestState;
import it.unicam.cs.exploremunicipalities.model.util.Coordinate;

public record ContestDTO(
        long id,
        String title,
        String description,
        Coordinate position,
        String start,
        String end,
        ContestState state,
        long winnerContributionId // se non c'è vincitore è -1
) {
}
