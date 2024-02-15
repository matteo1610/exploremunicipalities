package it.unicam.cs.exploremunicipalities.model.content;

import it.unicam.cs.exploremunicipalities.model.util.CoordinatePoint;
import it.unicam.cs.exploremunicipalities.model.util.Municipality;

import java.util.*;

/**
 * A point of the municipality.
 */
public class Point {
    private final UUID id;
    private final CoordinatePoint position;
    private final Municipality municipality;
    private final Set<Contribution> contributions;

    /**
     * Creates a new point.
     *
     * @param position the position of the point
     * @param municipality the municipality of the point
     */
    public Point(CoordinatePoint position, Municipality municipality){
        this.id=UUID.randomUUID();
        this.position=position;
        this.municipality=municipality;
        this.contributions = new HashSet<>();
    }

    public UUID getId() {
        return id;
    }

    public CoordinatePoint getPosition() {
        return position;
    }


    public Municipality getMunicipality() {
        return municipality;
    }

    public Set<Contribution> getContributions() {
        return contributions;
    }

    /**
     * Adds a contribution to the point.
     *
     * @param contribution the contribution to add
     * @return true if the contribution is added, false otherwise
     */
    public boolean addContribution(Contribution contribution) {
        return contributions.add(contribution);
    }

    /**
     * Removes a contribution from the point.
     *
     * @param contribution the contribution to remove
     * @return true if the contribution is removed, false otherwise
     */
    public boolean removeContribution(Contribution contribution) {
        return contributions.remove(contribution);
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