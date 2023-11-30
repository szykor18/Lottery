package pl.lotto.infrastructure.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import pl.lotto.infrastructure.loginandregister.controller.dto.JwtResponseDto;
import pl.lotto.infrastructure.loginandregister.controller.dto.TokenRequestDto;
import java.time.*;


@Component
@AllArgsConstructor
public class JwtAuthenticator {
    private final AuthenticationManager authenticationManager;
    private final Clock clock;
    private final JwtConfigurationProperties properties;

    public JwtResponseDto authenticateAndGenerateToken(TokenRequestDto tokenRequestDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                tokenRequestDto.username(), tokenRequestDto.password()));
        User user = (User) authenticate.getPrincipal();
        String username = user.getUsername();
        String token = createToken(user);
        return JwtResponseDto.builder().username(username).token(token).build();
    }

    private String createToken(User user) {
        String secretKey = properties.secretKey();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Instant now = LocalDateTime.now(clock).toInstant(ZoneOffset.UTC);
        Instant expiresAt = now.plus(Duration.ofDays(properties.expirationDays()));
        String issuer = properties.issuer();
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .withIssuer(issuer)
                .sign(algorithm);
    }
}
