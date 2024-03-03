package it.unicam.cs.exploremunicipalities.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateItineraryRequest(
        @NotNull(message = "Title of the itinerary cannot be null")
        String title,
        @NotNull(message = "Description of the itinerary cannot be null")
        String description,
        Set<Long> contributions

        // TODO aggiungere il campo per inserire i file multimediali

) {
}