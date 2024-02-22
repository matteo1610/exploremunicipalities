package it.unicam.cs.exploremunicipalities.controller.dto.request;

import it.unicam.cs.exploremunicipalities.model.util.CoordinatePoint;

public record CreatePOIRequest(
        CoordinatePoint coordinatePoint,
        String title,
        String description
        // TODO aggiungere il campo per inserire i file multimediali
) {
}
