package it.unicam.cs.exploremunicipalities.model.osm;

import it.unicam.cs.exploremunicipalities.model.content.Point;
import it.unicam.cs.exploremunicipalities.model.util.Coordinate;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A proxy for the OSM service.
 */
public class OSMProxy implements OSMServiceInterface {
    private final OSMService osm;
    private final Map<Point, Boolean> cache;
    private final static int CACHE_SIZE_LIMIT = 100;

    /**
     * Creates a new OSM proxy.
     *
     * @param osm the OSM service
     */
    public OSMProxy(OSMService osm) {
        this.osm = osm;
        this.cache = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Point, Boolean> eldest) {
                return size() > CACHE_SIZE_LIMIT;
            }
        };
    }

    @Override
    public boolean isPointInMunicipality(Coordinate point, Municipality municipality) throws Exception {
        Point p = new Point(point, municipality);
        if(this.cache.containsKey(p)) {
            return this.cache.get(p);
        }
        boolean result = this.osm.isPointInMunicipality(point, municipality);
        this.cache.put(p, result);
        return result;
    }
}
