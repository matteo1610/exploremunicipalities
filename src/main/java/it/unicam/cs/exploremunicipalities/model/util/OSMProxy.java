package it.unicam.cs.exploremunicipalities.model.util;

import java.util.HashMap;
import java.util.Map;

public class OSMProxy implements OSMService {
    private final OSM osm;
    private final Map<CoordinatePoint, String> cache;

    public OSMProxy(OSM osm) {
        this.osm = osm;
        this.cache = new HashMap<>();
    }

    @Override
    public boolean isPointInMunicipality(CoordinatePoint point, String municipalityName) throws Exception {
        if(this.cache.containsKey(point)) {
            return this.cache.get(point).equals(municipalityName);
        }
        boolean result = this.osm.isPointInMunicipality(point, municipalityName);
        if (result) {
            this.cache.put(point, municipalityName);
        }
        return result;
    }
}
