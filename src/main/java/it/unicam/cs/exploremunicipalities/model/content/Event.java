package it.unicam.cs.exploremunicipalities.model.content;

import it.unicam.cs.exploremunicipalities.model.user.User;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * An event in the municipality.
 */
public class Event extends Contribution {
    private LocalDateTime start;
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

    public LocalDateTime getStart() {
        return this.start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
