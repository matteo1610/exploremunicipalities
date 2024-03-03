package it.unicam.cs.exploremunicipalities.dto.request;

import it.unicam.cs.exploremunicipalities.model.util.Coordinate;
import jakarta.validation.constraints.NotNull;

public record CreateItineraryForPointRequest(
        @NotNull(message = "Position of itinerary cannot be null")
        Coordinate position,
        CreateItineraryRequest request
) {
}
