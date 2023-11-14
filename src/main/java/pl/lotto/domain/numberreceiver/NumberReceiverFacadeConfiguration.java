package pl.lotto.domain.numberreceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class NumberReceiverFacadeConfiguration {

    @Bean
    TicketRepository repository() {
        return new TicketRepository() {
            @Override
            public Ticket save(Ticket ticket) {
                return null;
            }

            @Override
            public List<Ticket> findAllTicketsByDrawDate(LocalDateTime date) {
                return null;
            }
        };
    }
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
