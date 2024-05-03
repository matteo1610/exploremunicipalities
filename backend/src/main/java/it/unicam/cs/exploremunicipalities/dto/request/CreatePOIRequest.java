package it.unicam.cs.exploremunicipalities.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreatePOIRequest(
        @NotNull(message = "Title of POI cannot be null")
        String title,
        @NotNull(message = "Description of POI cannot be null")
        String description

        // TODO aggiungere il campo per inserire i file multimediali
) {
}