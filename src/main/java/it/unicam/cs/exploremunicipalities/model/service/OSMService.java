package it.unicam.cs.exploremunicipalities.model.service;

import it.unicam.cs.exploremunicipalities.model.util.CoordinatePoint;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static java.net.URLEncoder.encode;

/**
 * This class provides methods to interact with the OpenStreetMap API.
 */
public class OSMService implements OSMServiceInterface {
    private static final String NOMINATIM_API_URL = "https://nominatim.openstreetmap.org/";

    /**
     * Returns the coordinate point of the municipality with the given name.
     * @param municipalityName the name of the municipality
     * @return the coordinate point of the municipality
     * @throws Exception if an error occurs
     */
    public CoordinatePoint getCoordinatePointOfMunicipality(String municipalityName) throws Exception {
        String encodedQuery = encode(municipalityName, StandardCharsets.UTF_8);
        JSONObject response = this.getResponse(NOMINATIM_API_URL + "search?q=" + encodedQuery + "&format=json");
        return new CoordinatePoint(response.getDouble("lat"), response.getDouble("lon"));
    }

    @Override
    public boolean isPointInMunicipality(CoordinatePoint point, Municipality municipality) throws Exception {
        String lat = String.format("%.6f", point.latitude());
        String lon = String.format("%.6f", point.longitude());
        JSONObject response = this.getResponse(NOMINATIM_API_URL + "reverse?lat=" + lat + "&lon="
                + lon + "&format=json");
        return response.getString("display_name").contains(municipality.getName());
    }

    private JSONObject getResponse(String apiUrl) throws Exception{
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            return new JSONObject(reader.lines().collect(Collectors.joining()));
        }
    }
}
