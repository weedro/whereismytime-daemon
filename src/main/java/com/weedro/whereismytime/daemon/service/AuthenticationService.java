package com.weedro.whereismytime.daemon.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weedro.whereismytime.daemon.dto.AuthRequestDto;
import com.weedro.whereismytime.daemon.dto.AuthResponseDto;
import com.weedro.whereismytime.daemon.exception.UnauthorizedException;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthenticationService {
    static final String AUTH_ENDPOINT = "http://localhost:7433/aus/api/v1/auth";

    public static String login(String username, String password) throws UnauthorizedException {
        HttpClient httpClient = HttpClient.newBuilder().build();
        ObjectMapper objectMapper = new ObjectMapper();

        AuthRequestDto authRequestDto = new AuthRequestDto(username, password);
        try {
            String loginJson = objectMapper.writeValueAsString(authRequestDto);

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(AUTH_ENDPOINT))
                            .setHeader("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(loginJson))
                            .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new UnauthorizedException("Invalid login credentials");
            }

            AuthResponseDto authResponseDto = objectMapper.readValue(response.body(), AuthResponseDto.class);
            return authResponseDto.token();
        }
        catch (ConnectException e) {
            throw new RuntimeException("Failed to connect to authentication server", e);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
