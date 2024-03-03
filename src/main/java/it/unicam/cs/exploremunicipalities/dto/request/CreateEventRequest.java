package it.unicam.cs.exploremunicipalities.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateEventRequest(
        @NotNull(message = "Title of the event cannot be null")
        String title,
        @NotNull(message = "Description of the event cannot be null")
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate

        // TODO aggiungere il campo per inserire i file multimediali
) {
}
