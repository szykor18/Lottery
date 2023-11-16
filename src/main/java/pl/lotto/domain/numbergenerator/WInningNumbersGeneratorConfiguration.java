package pl.lotto.domain.numbergenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.numberreceiver.NumberReceiverFacade;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
public class WInningNumbersGeneratorConfiguration {

    @Bean
    WinningNumbersGeneratorFacade winningNumbersGeneratorFacade(RandomNumbersGenerable randomNumbersGenerable, NumberReceiverFacade numberReceiverFacade, WinningNumbersRepository repository, WinningNumberGeneratorFacadeConfigurationProperties properties) {
        WinningNumbersValidator validator = new WinningNumbersValidator();
        return new WinningNumbersGeneratorFacade(randomNumbersGenerable, validator, numberReceiverFacade, repository, properties);
    }

    WinningNumbersGeneratorFacade createForTests(RandomNumbersGenerable generator, WinningNumbersRepository repository, NumberReceiverFacade numberReceiverFacade) {
        WinningNumberGeneratorFacadeConfigurationProperties properties = WinningNumberGeneratorFacadeConfigurationProperties.builder()
                .minBound(1)
                .maxBound(99)
                .count(6)
                .build();
        return winningNumbersGeneratorFacade(generator, numberReceiverFacade, repository, properties);
    }
}
