package it.unicam.cs.exploremunicipalities.model.content;

import it.unicam.cs.exploremunicipalities.model.user.User;

import java.io.File;
import java.util.Set;

/**
 * A point of interest of the municipality.
 */
public class PointOfInterest extends Contribution {
    /**
     * Creates a new contribution.
     *
     * @param title       the title of the contribution
     * @param description the description of the contribution
     * @param multimedia  the multimedia files of the contribution
     * @param author      the author of the contribution
     */
    public PointOfInterest(String title, String description, Set<File> multimedia, ContributionState state,
                           User author) {
        super(title, description, multimedia, state, author);
    }
}
