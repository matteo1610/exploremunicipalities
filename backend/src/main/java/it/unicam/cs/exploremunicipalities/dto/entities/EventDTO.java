package it.unicam.cs.exploremunicipalities.dto.entities;

import it.unicam.cs.exploremunicipalities.model.content.contribution.ContributionState;
import it.unicam.cs.exploremunicipalities.model.content.contribution.ContributionType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EventDTO extends ContributionDTO {
    private final String description;
    private final String start;
    private final String end;

    public EventDTO(long id, String title, String description, ContributionType type, ContributionState state,
                    LocalDateTime start, LocalDateTime end) {
        super(id, title, type, state);
        this.description = description;
        this.start = start.toString();
        this.end = end.toString();
    }
}
