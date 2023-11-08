package pl.lotto.domain.numbergenerator;
import lombok.AllArgsConstructor;
import pl.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import pl.lotto.domain.numberreveiver.NumberReceiverFacade;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
public class WinningNumbersGeneratorFacade {
    private RandomNumbersGenerable randomNumbersGenerator;
    private WinningNumbersValidator validator;
    private NumberReceiverFacade numberReceiverFacade;
    private WinningNumbersRepository winningNumbersRepository;
    public WinningNumbersDto generateWinningNumbers() {
        LocalDateTime drawDate = numberReceiverFacade.retrieveNextDrawDate();
        Set<Integer> winningNumbers = randomNumbersGenerator.generateSixRandomNumbers();
        validator.validate(winningNumbers);
        winningNumbersRepository.save(WinningNumbers.builder()
                        .winningNumbers(winningNumbers)
                        .drawDate(drawDate)
                        .build());
        return WinningNumbersDto.builder()
                .winningNumbers(winningNumbers)
                .build();
    }

    public WinningNumbersDto retrieveWinningNumbersByDate(LocalDateTime date) {
        WinningNumbers winningNumbersByDate = winningNumbersRepository.findWinningNumbersByDate(date)
                .orElseThrow(() -> new WinningNumbersNotFoundException("Not found"));
        return WinningNumbersDto.builder()
                .winningNumbers(winningNumbersByDate.winningNumbers())
                .drawDate(winningNumbersByDate.drawDate())
                .build();
    }

    public boolean areWinningNumbersGeneratedByDate() {
        LocalDateTime drawDate = numberReceiverFacade.retrieveNextDrawDate();
        return winningNumbersRepository.existsByDate(drawDate);
    }
}
