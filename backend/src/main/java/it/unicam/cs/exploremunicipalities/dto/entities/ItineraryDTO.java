package it.unicam.cs.exploremunicipalities.dto.entities;

import it.unicam.cs.exploremunicipalities.model.content.contribution.Contribution;
import it.unicam.cs.exploremunicipalities.model.content.contribution.ContributionState;
import it.unicam.cs.exploremunicipalities.model.content.contribution.ContributionType;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ItineraryDTO extends ContributionDTO {
    private final String description;
    private final Set<ContributionDTO> associatedContributions;

    public ItineraryDTO(long id, String title, String description, ContributionType type, ContributionState state,
                        Set<Contribution> contributions) {
        super(id, title, type, state);
        this.description = description;
        this.associatedContributions = new HashSet<>();
        for (Contribution c : contributions) {
            this.associatedContributions.add(c.toDTO());
        }
    }
}
