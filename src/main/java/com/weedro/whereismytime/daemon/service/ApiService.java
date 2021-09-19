package com.weedro.whereismytime.daemon.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weedro.whereismytime.daemon.dto.WastedTimeDtoWindow;
import com.weedro.whereismytime.daemon.entity.TrackedWindow;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiService {
    static final String TRACKER_ENDPOINT = "http://localhost:7432/wimt/api/v1/track";
    private final String accessToken;

    public ApiService(String accessToken) {
        this.accessToken = accessToken;
    }

    public void sendUpdates(Map<String, TrackedWindow> trackedWindows) {
        HttpClient httpClient = HttpClient.newBuilder().build();
        ObjectMapper objectMapper = new ObjectMapper();

        List<WastedTimeDtoWindow> windows = trackedWindows.values().stream()
                .map(w -> new WastedTimeDtoWindow(w.getWindow().owner().name(), w.getSecondsWasted()))
                .collect(Collectors.toList());

        try {
            String requestJson = objectMapper.writeValueAsString(windows);

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(TRACKER_ENDPOINT))
                            .setHeader("Content-Type", "application/json")
                            .setHeader("Authorization", "Bearer " + accessToken)
                            .POST(HttpRequest.BodyPublishers.ofString(requestJson))
                            .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

//            if (response.statusCode() != 200) {
//            }
        }
        catch (ConnectException e) {
            throw new RuntimeException("Failed to connect to tracker server", e);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
