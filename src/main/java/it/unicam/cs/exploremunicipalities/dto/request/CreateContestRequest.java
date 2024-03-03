package it.unicam.cs.exploremunicipalities.dto.request;

import it.unicam.cs.exploremunicipalities.model.util.Coordinate;
import jakarta.validation.constraints.NotNull;

public record CreateContestRequest(
        @NotNull(message = "Title of contest cannot be null")
        String title,
        @NotNull(message = "Description of contest cannot be null")
        String description,
        @NotNull(message = "Position of contest cannot be null")
        Coordinate position
) {
}
