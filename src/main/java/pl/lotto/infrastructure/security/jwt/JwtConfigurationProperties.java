package pl.lotto.infrastructure.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt.authentication")
public record JwtConfigurationProperties(String issuer, long expiresAt, String secretKey) {
}
