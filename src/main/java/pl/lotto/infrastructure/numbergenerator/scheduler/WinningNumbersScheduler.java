package pl.lotto.infrastructure.numbergenerator.scheduler;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.lotto.domain.numbergenerator.WinningNumbersGeneratorFacade;
import pl.lotto.domain.numbergenerator.dto.WinningNumbersDto;

@Log4j2
@AllArgsConstructor
@Component
public class WinningNumbersScheduler {

    private final WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;
    @Scheduled(cron = "${lotto.number-generator.frequency}")
    public WinningNumbersDto generateWinningNumbers() {
        log.info("winning numbers scheduler has started");
        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.generateWinningNumbers();
        log.info(winningNumbersDto.winningNumbers());
        log.info(winningNumbersDto.drawDate());
        return winningNumbersDto;
    }
}
