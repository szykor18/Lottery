package pl.lotto.domain.resultchecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.numbergenerator.WinningNumbersGeneratorFacade;
import pl.lotto.domain.numberreceiver.NumberReceiverFacade;

@Configuration
public class ResultCheckerConfiguration {

    @Bean
    public ResultCheckerFacade resultCheckerFacade(NumberReceiverFacade numberReceiverFacade, WinningNumbersGeneratorFacade winningNumbersGeneratorFacade, PlayerRepository playerRepository) {
        WinnersRetriever winnersRetriever = new WinnersRetriever();
        return new ResultCheckerFacade(numberReceiverFacade, winningNumbersGeneratorFacade, playerRepository, winnersRetriever);
    }
}
