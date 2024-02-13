package it.unicam.cs.exploremunicipalities.model.content;

import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.util.Municipality;

import java.io.File;
import java.util.List;

/**
 * An itinerary in the municipality.
 */
public class Itinerary extends Contribution {
    private final List<Contribution> contributions;

    /**
     * Creates a new contribution.
     *
     * @param title       the title of the contribution
     * @param description the description of the contribution
     * @param multimedia  the multimedia files of the contribution
     * @param state       the state of the contribution
     * @param author      the author of the contribution
     */
    public Itinerary(String title, String description, List<File> multimedia, ContributionState state, User author,
                     Municipality municipality, List<Contribution> contributions) {
        super(title, description, multimedia, state, author, municipality);
        this.contributions = contributions;
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    /**
     * Adds a contribution to the itinerary.
     *
     * @param contribution the contribution to add
     * @return true if the contribution is added, false otherwise
     */
    public boolean addContribution(Contribution contribution) {
        return contributions.add(contribution);
    }

    /**
     * Removes a contribution from the itinerary.
     *
     * @param contribution the contribution to remove
     * @return true if the contribution is removed, false otherwise
     */
    public boolean removeContribution(Contribution contribution) {
        return contributions.remove(contribution);
    }
}
