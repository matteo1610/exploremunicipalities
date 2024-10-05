package it.unicam.cs.exploremunicipalities.dto.entities;

import it.unicam.cs.exploremunicipalities.model.content.contribution.ContributionState;
import it.unicam.cs.exploremunicipalities.model.content.contribution.ContributionType;
import lombok.Getter;

@Getter
public class PointOfInterestDTO extends ContributionDTO {
    private final String description;

    public PointOfInterestDTO(long id, String title, String description, ContributionType type, ContributionState state) {
        super(id, title, type, state);
        this.description = description;
    }
}
