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

    WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;
    @Scheduled(cron = "*/10 * * * * *")
    public void generateWinningNumbers() {
        log.info("winning numbers scheduler has started");
        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.generateWinningNumbers();
        log.info(winningNumbersDto.winningNumbers());
    }
}
