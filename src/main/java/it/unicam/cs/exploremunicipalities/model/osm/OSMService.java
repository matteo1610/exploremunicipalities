package it.unicam.cs.exploremunicipalities.model.osm;

import it.unicam.cs.exploremunicipalities.model.util.Coordinate;
import it.unicam.cs.exploremunicipalities.model.content.Municipality;
import org.json.JSONArray;
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
     * @param name the name of the municipality
     * @param province the province of the municipality
     * @return the coordinate point of the municipality
     * @throws Exception if an error occurs while getting the response
     * @throws IllegalArgumentException if the municipality does not exist
     */
    public Coordinate getCoordinatePointOfMunicipality(String name, String province) throws Exception {
        String encodedQuery = encode(name, StandardCharsets.UTF_8);
        JSONArray response = this.getArrayResponse(NOMINATIM_API_URL + "search?q=" + encodedQuery + "&format=json");
        for (int i = 0; i < response.length(); i++) {
            JSONObject result = response.getJSONObject(i);
            String displayName = result.getString("display_name").toLowerCase();
            if (displayName.contains(name.toLowerCase()) && displayName.contains(province.toLowerCase())) {
                return new Coordinate(result.getDouble("lat"), result.getDouble("lon"));
            }
        }
        throw new IllegalArgumentException("Municipality not found");
    }

    @Override
    public boolean isPointInMunicipality(Coordinate point, Municipality municipality) throws Exception {
        String lat = String.format("%.6f", point.latitude());
        String lon = String.format("%.6f", point.longitude());
        JSONObject result = this.getResponseObject(NOMINATIM_API_URL + "reverse?lat=" + lat + "&lon=" + lon
                + "&format=json");
        return result.getString("display_name").toLowerCase().contains(municipality.getName().toLowerCase());
    }

    private JSONArray getArrayResponse(String apiUrl) throws Exception{
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            return new JSONArray(reader.lines().collect(Collectors.joining()));
        }
    }

    private JSONObject getResponseObject(String apiUrl) throws Exception{
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            return new JSONObject(reader.lines().collect(Collectors.joining()));
        }
    }
}
