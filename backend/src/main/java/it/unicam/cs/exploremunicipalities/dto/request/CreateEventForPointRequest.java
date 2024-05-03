package it.unicam.cs.exploremunicipalities.dto.request;

import it.unicam.cs.exploremunicipalities.model.util.Coordinate;
import jakarta.validation.constraints.NotNull;

public record CreateEventForPointRequest(
        @NotNull(message = "Position of event cannot be null")
        Coordinate position,
        CreateEventRequest request
) {
}
