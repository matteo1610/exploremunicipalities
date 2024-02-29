package it.unicam.cs.exploremunicipalities.dto.request;

public record CreatePOIContestRequest(
        long contestId,
        String title,
        String description
        // TODO aggiungere il campo per inserire i file multimediali
) {
}