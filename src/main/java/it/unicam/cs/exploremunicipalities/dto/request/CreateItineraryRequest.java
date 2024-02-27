package it.unicam.cs.exploremunicipalities.dto.request;

import it.unicam.cs.exploremunicipalities.model.util.Coordinate;

import java.util.Set;

public record CreateItineraryRequest(
        Coordinate coordinate,
        String title,
        String description,
        Set<Long> contributions
        // TODO aggiungere il campo per inserire i file multimediali

) {
}
