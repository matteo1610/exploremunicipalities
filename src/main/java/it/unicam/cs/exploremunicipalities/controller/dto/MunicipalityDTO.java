package it.unicam.cs.exploremunicipalities.controller.dto;

import it.unicam.cs.exploremunicipalities.model.util.CoordinatePoint;

public record MunicipalityDTO(long id, String name, String province, CoordinatePoint identityPoint) {
}
