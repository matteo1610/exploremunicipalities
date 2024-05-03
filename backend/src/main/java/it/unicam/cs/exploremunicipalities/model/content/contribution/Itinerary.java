package it.unicam.cs.exploremunicipalities.model.content.contribution;

import it.unicam.cs.exploremunicipalities.dto.ContributionDTO;
import it.unicam.cs.exploremunicipalities.dto.ItineraryDTO;
import it.unicam.cs.exploremunicipalities.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.Set;

/**
 * An itinerary in the municipality.
 */
@Getter
@NoArgsConstructor
@Entity
public class Itinerary extends Contribution {
    @ManyToMany
    private Set<Contribution> contributions;

    /**
     * Creates a new itinerary.
     *
     * @param title       the title of the contribution
     * @param description the description of the contribution
     * @param multimedia  the multimedia files of the contribution
     * @param author      the author of the contribution
     * @param contributions the contributions of the itinerary
     */
    public Itinerary(String title, String description, Set<File> multimedia, User author, Set<Contribution>
            contributions) {
        super(title, description, multimedia, ContributionType.ITINERARY, author);
        this.contributions = contributions;
    }

    /**
     * Adds a contribution to the itinerary.
     *
     * @param contribution the contribution to add
     * @return true if the contribution is added, false otherwise
     */
    public boolean addContribution(Set<Contribution> contribution) {
        return contributions.addAll(contribution);
    }

    /**
     * Removes a contribution from the itinerary.
     *
     * @param contribution the contribution to remove
     * @return true if the contribution is removed, false otherwise
     */
    public boolean removeContribution(Set<Contribution> contribution) {
        return contributions.removeAll(contribution);
    }

    @Override
    public ContributionDTO getDetails() {
        return new ItineraryDTO(this.getId(), this.getTitle(), this.getDescription(), this.getType(), this.getState(),
                this.contributions);
    }
}
