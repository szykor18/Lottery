package pl.lotto.infrastructure.numbergenerator.http;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.lotto.domain.numbergenerator.RandomNumbersGenerable;
import pl.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;
import org.springframework.http.HttpHeaders;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RandomNumberGeneratorRestTemplate implements RandomNumbersGenerable {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public SixRandomNumbersDto generateSixRandomNumbers() {
        String urlForService = getUrlForService("/api/v1.0/random");
        HttpHeaders httpHeaders = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(httpHeaders);
        final String url = UriComponentsBuilder.fromHttpUrl(urlForService)
                .queryParam("min", 1)
                .queryParam("max", 99)
                .queryParam("count", 25)
                .toUriString();
        ResponseEntity<List<Integer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {}
        );
        List<Integer> numbers = response.getBody();
        System.out.println(numbers);
        return SixRandomNumbersDto.builder().numbers(numbers.stream().collect(Collectors.toSet())).build();
    }

    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }
}
