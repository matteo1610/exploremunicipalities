package it.unicam.cs.exploremunicipalities.controller.dto.request;

import it.unicam.cs.exploremunicipalities.model.util.Coordinate;

import java.time.LocalDateTime;

public record CreateEventRequest(
        Coordinate coordinate,
        String title,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate
        // TODO aggiungere il campo per inserire i file multimediali
) {
}
