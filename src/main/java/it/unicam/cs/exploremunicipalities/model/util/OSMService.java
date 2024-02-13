package it.unicam.cs.exploremunicipalities.model.util;

public interface OSMService {
    boolean isPointInMunicipality(CoordinatePoint point, String municipalityName) throws Exception;
}
