package it.unicam.cs.exploremunicipalities.dto.entities;

import it.unicam.cs.exploremunicipalities.model.util.Coordinate;

public record MunicipalityDTO(long id, String name, String province, Coordinate identityPoint) {
}
