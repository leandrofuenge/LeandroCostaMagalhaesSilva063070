package com.app.music.integration;

import com.app.music.regional.dto.RegionalDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ArgusClientImpl implements ArgusClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ArgusClientImpl(
            RestTemplate restTemplate,
            @Value("${argus.api.url}") String baseUrl
    ) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<RegionalDTO> buscarRegionais() {

        return restTemplate.exchange(
                baseUrl + "/v1/regionais",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RegionalDTO>>() {}
        ).getBody();
    }
}
