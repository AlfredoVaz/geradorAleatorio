package com.geradorAleatorio.repository;

import com.geradorAleatorio.dto.RandomActivityDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geradorAleatorio.exception.RandomActivityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Repository
public class RandomActivityRepository {

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final String randomActivityUrl;

    public RandomActivityRepository(@Value("${external.bored.random-url}") String randomActivityUrl) {
        this.randomActivityUrl = randomActivityUrl;
    }

    public RandomActivityDto getRandomActivity() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(randomActivityUrl))
                .GET()
                .header(HttpHeaders.ACCEPT, "application/json")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(response.body(), RandomActivityDto.class);
            }
            throw new RandomActivityException("Não foi possível consultar uma nova atividade. Status: " + response.statusCode());
        } catch (Exception e) {
            throw new RandomActivityException("Falha ao consultar a API externa", e);
        }
    }
}

