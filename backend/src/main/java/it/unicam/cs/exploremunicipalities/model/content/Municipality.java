package it.unicam.cs.exploremunicipalities.model.content;

import it.unicam.cs.exploremunicipalities.dto.entities.MunicipalityDTO;
import it.unicam.cs.exploremunicipalities.model.util.Coordinate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a municipality with a name, a province and an identity point.
 */
@Getter
@NoArgsConstructor
@Entity
public class Municipality {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String province;
    @Embedded
    private Coordinate identityPoint;
    @OneToMany(cascade = CascadeType.REMOVE)
    private Set<Point> points;
    @OneToMany(cascade = CascadeType.REMOVE)
    private Set<Contest> contests;

    /**
     * Creates a new municipality with the given name, province and identity point.
     *
     * @param name the name of the municipality
     * @param province the province of the municipality
     * @param identityPoint the identity point of the municipality
     */
    public Municipality(String name, String province, Coordinate identityPoint) {
        this.name = name;
        this.province = province;
        this.identityPoint = identityPoint;
        this.points = new HashSet<>();
    }

    /**
     * Returns the DTO of the municipality.
     *
     * @return the DTO of the municipality
     */
    public MunicipalityDTO toDTO() {
        return new MunicipalityDTO(this.id, this.name, this.province, this.identityPoint);
    }

    /**
     * Adds a point to the municipality.
     *
     * @param point the point to add
     */
    public void addPoint(Point point) {
        this.points.add(point);
    }

    /**
     * Adds a contest to the municipality.
     *
     * @param contest the contest to add
     */
    public void addContest(Contest contest) {
        this.contests.add(contest);
    }
}
