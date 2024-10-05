package it.unicam.cs.exploremunicipalities.model.content;

import it.unicam.cs.exploremunicipalities.dto.entities.ContestDTO;
import it.unicam.cs.exploremunicipalities.model.content.contribution.Contribution;
import it.unicam.cs.exploremunicipalities.model.content.contribution.ContributionState;
import it.unicam.cs.exploremunicipalities.model.util.Coordinate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A contest in the municipality.
 */
@Getter
@NoArgsConstructor
@Entity
public class Contest {
    @Id
    @GeneratedValue
    private long id;
    private String title;
    private String description;
    @Embedded
    private Coordinate position;
    @OneToMany
    private Set<Contribution> contributions;
    @Column(name = "start_datetime")
    private LocalDateTime start;
    @Column(name = "end_datetime")
    private LocalDateTime end;
    @Setter
    private ContestState state;
    @Setter
    @OneToOne
    private Contribution winner;

    /**
     * Creates a new temporary contest.
     * @param title the title of the contest
     * @param description the description of the contest
     * @param start the start date of the contest
     * @param end the end date of the contest
     */
    public Contest(String title, String description, Coordinate position, LocalDateTime start, LocalDateTime end) {
        this.title = title;
        this.description = description;
        this.position = position;
        this.contributions = new HashSet<>();
        this.start = start;
        this.end = end;
        this.state = ContestState.CREATED;
    }

    /**
     * Creates a new contest.
     * @param title the title of the contest
     * @param description the description of the contest
     * @param position the position of the contest
     */
    public Contest(String title, String description, Coordinate position) {
        this(title, description, position, null, null);
    }

    /**
     * Returns the DTO of the contest.
     * @return the DTO of the contest
     */
    public ContestDTO toDTO() {
        return new ContestDTO(this.id, this.title, this.description, this.position, this.start != null ?
                this.start.toString() : null, this.end != null ? this.end.toString() : null,
                this.state, this.winner != null ? this.winner.getId() : -1);
    }

    /**g
     * Adds a contribution to the contest.
     * @param contribution the contribution to add
     * @throws IllegalArgumentException if the contest is not open
     */
    public void addContribution(Contribution contribution) {
        if (this.state != ContestState.OPEN) {
            throw new IllegalArgumentException("The contest is not open");
        }
        contribution.setState(ContributionState.INTO_CONTEST);
        this.contributions.add(contribution);
    }
}
