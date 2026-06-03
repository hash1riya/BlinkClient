package com.blink.blinkclient.network;

import com.blink.blinkclient.core.AuthResponse;
import com.blink.blinkclient.core.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class BlinkApiService {

    private static final String BASE_URL = "http://localhost:8080/blink";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 1. Unauthenticated Request (Login/Register)
    public AuthResponse authenticate(String username, String password) throws Exception {
        String payload = objectMapper.writeValueAsString(Map.of(
                "username", username,
                "password", password
        ));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/auth/auth"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Map<?, ?> responseMap = objectMapper.readValue(response.body(), Map.class);
            return new AuthResponse(
                    (String) responseMap.get("token"),
                    (String) responseMap.get("id"),
                    (String) responseMap.get("username"),
                    (String) responseMap.get("email")
            );
        } else {
            throw new RuntimeException("Authentication failed: Status " + response.statusCode());
        }
    }

    // 2. Authenticated Request (Getting Friends Data)
    public String getFriendsList(String userId) throws Exception {
        if (!SessionManager.isAuthenticated()) {
            throw new IllegalStateException("No active session found.");
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users/" + userId + "/friends/accepted"))
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // 3. Authenticated Request (Getting Rooms Where User Participates)
    public String getRoomsList(String userId) throws Exception {
        if (!SessionManager.isAuthenticated()) {
            throw new IllegalStateException("No active session found.");
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/rooms/userRooms/" + userId))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}