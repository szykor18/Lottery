package pl.lotto.infrastructure.numbergenerator.http;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import pl.lotto.domain.numbergenerator.RandomNumbersGenerable;
import pl.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Log4j2
public class RandomNumberGeneratorRestTemplate implements RandomNumbersGenerable {
    private static final int VALID_AMOUNT_OF_NUMBERS = 6;
    private static final String RANDOM_NUMBER_SERVICE_PATH = "/api/v1.0/random";

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public SixRandomNumbersDto generateSixRandomNumbers(int minBound, int maxBound, int count) {
        log.info("Started fetching winning numbers using http client");
        HttpHeaders httpHeaders = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(httpHeaders);
        try {
            final ResponseEntity<List<Integer>> response = makeGetRequestFromClient(requestEntity, minBound, maxBound, count);
            Set<Integer> sixDistinctNumbers = getSixDistinctNumbers(response);
            if (sixDistinctNumbers.size() != VALID_AMOUNT_OF_NUMBERS) {
                log.error("Set size is less than {" + VALID_AMOUNT_OF_NUMBERS + "} Have to request one more time");
                return generateSixRandomNumbers(minBound, maxBound, count);
            }
            return SixRandomNumbersDto.builder().numbers(sixDistinctNumbers).build();
        } catch (ResourceAccessException exception) {
            log.error("Error while fetching winning number using http client" + exception.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Set<Integer> getSixDistinctNumbers(ResponseEntity<List<Integer>> response) {
        List<Integer> fetchedNumbers = response.getBody();
        if (fetchedNumbers == null) {
            log.error("Response Body was null returning empty collection");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        Set<Integer> distinctNumbers = new HashSet<>(fetchedNumbers);
        log.info("Success Response Body Returned: " + response);
        return distinctNumbers.stream()
                .limit(VALID_AMOUNT_OF_NUMBERS)
                .collect(Collectors.toSet());
    }

    private ResponseEntity<List<Integer>> makeGetRequestFromClient(HttpEntity<HttpHeaders> requestEntity, int minBound, int maxBound, int count) {
        final String url = UriComponentsBuilder.fromHttpUrl(getUrlForService(RANDOM_NUMBER_SERVICE_PATH))
                .queryParam("min", minBound)
                .queryParam("max", maxBound)
                .queryParam("count", count)
                .toUriString();
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
    }

    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }
}
