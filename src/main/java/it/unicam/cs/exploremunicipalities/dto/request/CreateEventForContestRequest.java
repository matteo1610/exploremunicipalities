package it.unicam.cs.exploremunicipalities.dto.request;

import java.time.LocalDateTime;

public record CreateEventForContestRequest(
    long contestId,
    String title,
    String description,
    LocalDateTime startDate,
    LocalDateTime endDate
    // TODO aggiungere il campo per inserire i file multimediali
) {
}
