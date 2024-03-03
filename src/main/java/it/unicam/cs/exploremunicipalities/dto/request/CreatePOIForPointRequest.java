package it.unicam.cs.exploremunicipalities.dto.request;

import it.unicam.cs.exploremunicipalities.model.util.Coordinate;
import jakarta.validation.constraints.NotNull;

public record CreatePOIForPointRequest(
        @NotNull(message = "Title of POI cannot be null")
        Coordinate position,
        CreatePOIRequest request
) {
}
