package it.unicam.cs.exploremunicipalities.model.content.contest;

import it.unicam.cs.exploremunicipalities.model.user.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A contest in the municipality.
 */
public class Contest {
    private final UUID id;
    private String title;
    private String description;
    private final Set<Registration> registrations;
    private LocalDateTime start;
    private LocalDateTime end;
    private ContestState state;
    private final User animator;

    /**
     * Creates a new contest.
     * @param title the title of the contest
     * @param description the description of the contest
     * @param start the start date of the contest
     * @param end the end date of the contest
     * @param animator the animator of the contest
     */
    public Contest(String title, String description, LocalDateTime start, LocalDateTime end, User animator) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.registrations = new HashSet<>();
        this.start = start;
        this.end = end;
        this.state = ContestState.CREATED;
        this.animator = animator;
    }

    public UUID getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Registration> getRegistrations() {
        return this.registrations;
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

    public ContestState getState() {
        return this.state;
    }

    public void setState(ContestState state) {
        this.state = state;
    }

    public User getAnimator() {
        return this.animator;
    }

    /**
     * Adds a participation to the contest.
     * @param registration the participation to add
     */
    public void addRegistration(Registration registration) {
        this.registrations.add(registration);
    }

    /**
     * Removes a participation from the contest.
     * @param registration the participation to remove
     */
    public void removeRegistration(Registration registration) {
        this.registrations.remove(registration);
    }
}
