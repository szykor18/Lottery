package pl.lotto.infrastructure.security.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pl.lotto.domain.loginandregister.LoginAndRegisterFacade;

import java.time.Clock;

@Configuration
public class JwtSecurityConfig {

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    UserDetailsService userDetailsService(LoginAndRegisterFacade loginAndRegisterFacade) {
        return new LoginUserDetailsService(loginAndRegisterFacade);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.authorizeHttpRequests(http -> http
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("/token/**").permitAll()
                        .requestMatchers("/register/**").permitAll()
                        .requestMatchers("/inputNumbers/**").permitAll()
                        .requestMatchers("/results/**").permitAll()
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .anyRequest().authenticated())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .httpBasic(httpBasic -> httpBasic.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();
    }
}
