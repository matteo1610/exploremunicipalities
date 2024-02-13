package it.unicam.cs.exploremunicipalities.model.content;

import it.unicam.cs.exploremunicipalities.model.user.User;
import it.unicam.cs.exploremunicipalities.model.util.Municipality;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * A contribution to the municipality.
 */
public class Contribution {
    private final UUID id;
    private String title;
    private String description;
    private final List<File> multimedia;
    private final Date creationDate;
    private ContributionState state;
    private final User author;
    private final Municipality municipality;

    /**
     * Creates a new contribution.
     * @param title the title of the contribution
     * @param description the description of the contribution
     * @param multimedia the multimedia files of the contribution
     * @param state the state of the contribution
     * @param author the author of the contribution
     * @param municipality the municipality of the contribution
     */
    public Contribution(String title, String description, List<File> multimedia, ContributionState state, User author,
                        Municipality municipality) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.multimedia = multimedia;
        this.creationDate = new Date();
        this.state = state;
        this.author = author;
        this.municipality = municipality;
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

    public List<File> getMultimedia() {
        return this.multimedia;
    }

    /**
     * Add files to the contribution
     * @param files the files to add
     */
    public void addFiles(List<File> files) {
        this.multimedia.addAll(files);
    }

    /**
     * Remove files from the contribution
     * @param files the files to remove
     */
    public void removeFiles(List<File> files) {
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

    public Municipality getMunicipality() {
        return this.municipality;
    }
}
