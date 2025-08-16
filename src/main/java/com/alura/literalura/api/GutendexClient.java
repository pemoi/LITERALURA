package com.alura.literalura.api;

import com.alura.literalura.dto.GutendexBook;
import com.alura.literalura.dto.GutendexResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

@Component
public class GutendexClient {

    private final WebClient webClient;

    public GutendexClient() {
        this.webClient = WebClient.builder()
                .baseUrl("https://gutendex.com")
                .build();
    }

    public java.util.List<GutendexBook> searchByTitle(String title) {
        String encoded = URLEncoder.encode(title, StandardCharsets.UTF_8);
        Mono<GutendexResponse> mono = webClient.get()
                .uri("/books?search=" + encoded)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(GutendexResponse.class)
                .timeout(Duration.ofSeconds(15));

        GutendexResponse response = mono.block();
        if (response == null || response.getResults() == null) {
            return java.util.List.of();
        }
        return response.getResults();
    }
}
