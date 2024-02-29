package it.unicam.cs.exploremunicipalities.dto.request;

import java.util.Set;

public record CreateItineraryForContestRequest(
        long contestId,
        String title,
        String description,
        Set<Long> contributions
        // TODO aggiungere il campo per inserire i file multimediali

) {
}