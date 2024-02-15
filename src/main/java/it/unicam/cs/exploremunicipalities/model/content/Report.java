package it.unicam.cs.exploremunicipalities.model.content;

import it.unicam.cs.exploremunicipalities.model.user.User;

import java.util.UUID;

/**
 * A report for a contribution.
 */
public class Report {
    private final UUID id;
    private final Contribution contribution;
    private final String problem;
    private final String description;
    private final User author;

    /**
     * Creates a new report for the given contribution, with the given problem, description and author.
     * @param contribution the contribution to the report
     * @param problem the problem of the report
     * @param description the description of the report
     * @param author the author of the report
     */
    public Report(Contribution contribution, String problem, String description, User author) {
        this.id = UUID.randomUUID();
        this.contribution = contribution;
        this.problem = problem;
        this.description = description;
        this.author = author;
    }

    public UUID getId() {
        return this.id;
    }

    public Contribution getContribution() {
        return this.contribution;
    }

    public String getProblem() {
        return this.problem;
    }

    public String getDescription() {
        return this.description;
    }

    public User getAuthor() {
        return this.author;
    }
}
