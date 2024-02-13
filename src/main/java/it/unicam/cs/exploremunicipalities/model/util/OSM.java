package it.unicam.cs.exploremunicipalities.model.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class OSM implements OSMService {
    private static final String NOMINATIM_API_URL = "https://nominatim.openstreetmap.org/";

    @Override
    public CoordinatePoint getCoordinatePointOfMunicipality(String municipalityName) throws Exception {
        String encodedQuery = java.net.URLEncoder.encode(municipalityName, StandardCharsets.UTF_8);
        String response = this.getResponse(NOMINATIM_API_URL + "search?q=" + encodedQuery + "&format=json");
        JSONObject jsonObject = new JSONObject(response);
        return new CoordinatePoint(jsonObject.getDouble("lat"), jsonObject.getDouble("lon"));
    }

    @Override
    public boolean isPointInMunicipality(CoordinatePoint point, String municipalityName) throws Exception {
        String lat = String.format("%.6f", point.latitude());
        String lon = String.format("%.6f", point.longitude());
        String response = this.getResponse(NOMINATIM_API_URL + "reverse?lat=" + lat + "&lon=" + lon + "&format=json");
        JSONObject jsonObject = new JSONObject(response);
        return response.contains(municipalityName);
    }

    private String getResponse(String apiUrl) throws Exception{
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            return reader.lines().collect(Collectors.joining());
        }
    }
}
