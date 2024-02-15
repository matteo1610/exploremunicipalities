package it.unicam.cs.exploremunicipalities.model.content;

import it.unicam.cs.exploremunicipalities.model.user.User;

import java.io.File;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * A contribution to the municipality.
 */
public abstract class Contribution {
    private final UUID id;
    private String title;
    private String description;
    private final Set<File> multimedia;
    private final Date creationDate;
    private ContributionState state;
    private final User author;

    /**
     * Creates a new contribution.
     * @param title the title of the contribution
     * @param description the description of the contribution
     * @param multimedia the multimedia files of the contribution
     * @param state the state of the contribution
     * @param author the author of the contribution
     */
    public Contribution(String title, String description, Set<File> multimedia, ContributionState state, User author) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.multimedia = multimedia;
        this.creationDate = new Date();
        this.state = state;
        this.author = author;
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

    public Set<File> getMultimedia() {
        return this.multimedia;
    }

    /**
     * Add files to the contribution
     * @param files the files to add
     */
    public void addFiles(Set<File> files) {
        this.multimedia.addAll(files);
    }

    /**
     * Remove files from the contribution
     * @param files the files to remove
     */
    public void removeFiles(Set<File> files) {
        this.multimedia.removeAll(files);
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public ContributionState getState() {
        return this.state;
    }

    public void setState(ContributionState state) {
        this.state = state;
    }

    public User getAuthor() {
        return this.author;
    }
}
