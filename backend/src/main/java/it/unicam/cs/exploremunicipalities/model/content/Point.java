package it.unicam.cs.exploremunicipalities.model.content;

import it.unicam.cs.exploremunicipalities.dto.PointDTO;
import it.unicam.cs.exploremunicipalities.model.content.contribution.Contribution;
import it.unicam.cs.exploremunicipalities.model.content.contribution.ContributionState;
import it.unicam.cs.exploremunicipalities.model.util.Coordinate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * A point of the municipality.
 */
@Getter
@NoArgsConstructor
@Entity
public class Point {
    @Id
    @GeneratedValue
    private long id;
    @Embedded
    private Coordinate position;
    @ManyToOne
    private Municipality municipality;
    @OneToMany(cascade = CascadeType.REMOVE)
    private Set<Contribution> contributions;

    /**
     * Creates a new point.
     *
     * @param position the position of the point
     * @param municipality the municipality of the point
     */
    public Point(Coordinate position, Municipality municipality){
        this.position=position;
        this.municipality=municipality;
        this.contributions = new HashSet<>();
    }

    /**
     * Returns the DTO of the point.
     * @return the DTO of the point
     */
    public PointDTO toDTO() {
        return new PointDTO(this.id, this.position);
    }

    /**
     * Adds a contribution to the point.
     *
     * @param contribution the contribution to add
     */
    public void addContribution(Contribution contribution) {
        contribution.setState(ContributionState.APPROVED);
        this.contributions.add(contribution);
    }

    /**
     * Adds a contribution in pending to the point.
     *
     * @param contribution the contribution to add
     */
    public void addPendingContribution(Contribution contribution) {
        contribution.setState(ContributionState.PENDING);
        this.contributions.add(contribution);
    }

    /**
     * Removes a contribution from the point.
     *
     * @param contribution the contribution to remove
     * @return true if the contribution is removed, false otherwise
     */
    public boolean removeContribution(Contribution contribution) {
        return this.contributions.remove(contribution);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(this.position, point.position) && Objects.equals(this.municipality, point.municipality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.position, this.municipality);
    }
}
