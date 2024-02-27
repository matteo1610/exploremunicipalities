package it.unicam.cs.exploremunicipalities.dto.request;

import it.unicam.cs.exploremunicipalities.model.util.Coordinate;

public record CreatePOIRequest(
        Coordinate coordinate,
        String title,
        String description
        // TODO aggiungere il campo per inserire i file multimediali
) {
}
