package it.unicam.cs.exploremunicipalities.model.content.contribution;

import it.unicam.cs.exploremunicipalities.dto.ContributionDTO;
import it.unicam.cs.exploremunicipalities.dto.PointOfInterestDTO;
import it.unicam.cs.exploremunicipalities.model.user.User;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.Set;

/**
 * A point of interest of the municipality.
 */
@NoArgsConstructor
@Entity
public class PointOfInterest extends Contribution {
    /**
     * Creates a new point of interest.
     *
     * @param title       the title of the contribution
     * @param description the description of the contribution
     * @param multimedia  the multimedia files of the contribution
     * @param author      the author of the contribution
     */
    public PointOfInterest(String title, String description, Set<File> multimedia, User author) {
        super(title, description, multimedia, ContributionType.POINT_OF_INTEREST, author);
    }

    @Override
    public ContributionDTO getDetails() {
        return new PointOfInterestDTO(this.getId(), this.getTitle(), this.getDescription(), this.getType(),
                this.getState());
    }
}
