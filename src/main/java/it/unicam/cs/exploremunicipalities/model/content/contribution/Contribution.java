package it.unicam.cs.exploremunicipalities.model.content.contribution;

import it.unicam.cs.exploremunicipalities.dto.ContributionDTO;
import it.unicam.cs.exploremunicipalities.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.util.Date;
import java.util.Set;

/**
 * A contribution to the municipality.
 */
@Getter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Contribution {
    @Id
    @GeneratedValue
    private long id;
    @Setter
    private String title;
    @Setter
    private String description;
    @ElementCollection
    private Set<File> multimedia;
    private Date creationDate;
    private ContributionType type;
    @Setter
    private ContributionState state;
    @ManyToOne
    private User author;

    /**
     * Creates a new contribution.
     * @param title the title of the contribution
     * @param description the description of the contribution
     * @param type the type of the contribution
     * @param multimedia the multimedia files of the contribution
     * @param author the author of the contribution
     */
    public Contribution(String title, String description, Set<File> multimedia, ContributionType type, User author) {
        this.title = title;
        this.description = description;
        this.multimedia = multimedia;
        this.type = type;
        this.creationDate = new Date();
        this.author = author;
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

    public ContributionDTO toDTO() {
        return new ContributionDTO(this.id, this.title, this.type, this.state);
    }
}
