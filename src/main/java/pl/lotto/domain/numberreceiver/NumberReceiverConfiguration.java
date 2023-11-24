package pl.lotto.domain.numberreceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;

@Configuration
public class NumberReceiverConfiguration {

    @Bean
    HashGenerable hashGenerable() {
        return new HashGenerator();
    }

    @Bean
    public NumberReceiverFacade numberReceiverFacade(TicketRepository repository, DrawDateFacade drawDateFacade, HashGenerable hashGenerator) {
        NumberValidator validator = new NumberValidator();
        return new NumberReceiverFacade(validator, repository, hashGenerator, drawDateFacade);
    }
}
