package pl.lotto.domain.drawdategenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.*;

@Configuration
public class DrawDateConfiguration {

    @Bean
    public DrawDateFacade drawDateFacade() {
        return new DrawDateFacade(drawDateGenerator());
    }
    @Bean
    DrawDateGenerator drawDateGenerator() {
        return new DrawDateGenerator(clock());
    }

    @Bean
    @Primary
    Clock clock() {
        return Clock.systemUTC();
    }
}
