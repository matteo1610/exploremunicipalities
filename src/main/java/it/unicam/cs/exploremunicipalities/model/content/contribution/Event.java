package it.unicam.cs.exploremunicipalities.model.content.contribution;

import it.unicam.cs.exploremunicipalities.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * An event in the municipality.
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Event extends Contribution {
    @Column(name = "start_datetime")
    private LocalDateTime start;
    @Column(name = "end_datetime")
    private LocalDateTime end;

    /**
     * Creates a new contribution.
     *
     * @param title       the title of the contribution
     * @param description the description of the contribution
     * @param multimedia  the multimedia files of the contribution
     * @param author      the author of the contribution
     */
    public Event(String title, String description, Set<File> multimedia, ContributionState state, User author,
                 LocalDateTime start, LocalDateTime end) {
        super(title, description, multimedia, state, author);
        this.start = start;
        this.end = end;
    }
}
