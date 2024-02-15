package it.unicam.cs.exploremunicipalities.model.util;

import it.unicam.cs.exploremunicipalities.model.util.CoordinatePoint;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * This class represents a municipality with a name, a province and an identity point.
 */
@Getter
@NoArgsConstructor
@Entity
public final class Municipality {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String province;
    private CoordinatePoint identityPoint;

    /**
     * Creates a new municipality with the given name, province and identity point.
     *
     * @param name the name of the municipality
     * @param province the province of the municipality
     * @param identityPoint the identity point of the municipality
     */
    public Municipality(String name, String province, CoordinatePoint identityPoint) {
        this.name = name;
        this.province = province;
        this.identityPoint = identityPoint;
    }
}
