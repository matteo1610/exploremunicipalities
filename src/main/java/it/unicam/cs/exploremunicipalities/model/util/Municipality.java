package it.unicam.cs.exploremunicipalities.model.util;

import java.util.UUID;

/**
 * This class represents a municipality with a name, a province and an identity point.
 */
public final class Municipality {
    private final UUID id;
    private final String name;
    private final String province;
    private final CoordinatePoint identityPoint;

    /**
     * Creates a new municipality with the given name, province and identity point.
     *
     * @param name the name of the municipality
     * @param province the province of the municipality
     * @param identityPoint the identity point of the municipality
     */
    public Municipality(String name, String province, CoordinatePoint identityPoint) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.province = province;
        this.identityPoint = identityPoint;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getProvince() {
        return this.province;
    }

    public CoordinatePoint getIdentityPoint() {
        return this.identityPoint;
    }
}
