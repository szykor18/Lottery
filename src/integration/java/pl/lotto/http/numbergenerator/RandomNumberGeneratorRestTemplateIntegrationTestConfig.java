package pl.lotto.http.numbergenerator;

import org.springframework.web.client.RestTemplate;
import pl.lotto.domain.numbergenerator.RandomNumbersGenerable;
import pl.lotto.infrastructure.numbergenerator.http.RandomGeneratorClientConfig;
import pl.lotto.infrastructure.numbergenerator.http.RandomNumberGeneratorRestTemplateConfigurationProperties;
import pl.lotto.infrastructure.numbergenerator.http.RestTemplateResponseErrorHandler;

public class RandomNumberGeneratorRestTemplateIntegrationTestConfig extends RandomGeneratorClientConfig {

    RandomNumbersGenerable remoteNumberGeneratorClient(RandomNumberGeneratorRestTemplateConfigurationProperties properties) {
        RestTemplate restTemplate = restTemplate(new RestTemplateResponseErrorHandler(), properties);
        return remoteNumberGeneratorClient(restTemplate, properties);
    }
}
