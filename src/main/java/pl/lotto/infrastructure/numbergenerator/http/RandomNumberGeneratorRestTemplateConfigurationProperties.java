package pl.lotto.infrastructure.numbergenerator.http;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lotto.number-generator.http.client.config")
@Builder
public record RandomNumberGeneratorRestTemplateConfigurationProperties(String uri,
                                                                       String port,
                                                                       long connectionTimeout,
                                                                       long readTimeout) {
}
