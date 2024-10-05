package it.unicam.cs.exploremunicipalities.dto.entities;

import it.unicam.cs.exploremunicipalities.model.content.contribution.ContributionState;
import it.unicam.cs.exploremunicipalities.model.content.contribution.ContributionType;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ContributionDTO {
        private long id;
        private String title;
        private ContributionType type;
        private ContributionState state;
}
