package it.unicam.cs.exploremunicipalities.controller.dto;

import it.unicam.cs.exploremunicipalities.model.content.contribution.ContributionState;
import it.unicam.cs.exploremunicipalities.model.content.contribution.ContributionType;

/**
 * A DTO for a contribution.
 */
public record ContributionDTO(long id, String title, ContributionType type, ContributionState state) {
}
