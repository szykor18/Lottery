package pl.lotto.infrastructure.resultchecker.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.lotto.domain.numbergenerator.WinningNumbersGeneratorFacade;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;
import pl.lotto.domain.resultchecker.dto.PlayersResultsDto;

@AllArgsConstructor
@Component
@Log4j2
public class ResultCheckerScheduler {

    private final ResultCheckerFacade resultCheckerFacade;
    private final WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;

    @Scheduled(cron = "${lotto.result-checker.frequency}")
    public PlayersResultsDto generateResults() {
        log.info("result checker scheduler has started");
        if (!winningNumbersGeneratorFacade.areWinningNumbersGeneratedByDate()) {
            log.error("winning numbers are not generated");
            throw new RuntimeException("Winning numbers are not generated");
        }
        PlayersResultsDto playersResultsDto = resultCheckerFacade.generateResults();
        log.info(playersResultsDto);
        return playersResultsDto;
    }
}
