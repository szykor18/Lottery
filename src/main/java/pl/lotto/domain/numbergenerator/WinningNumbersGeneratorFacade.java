package pl.lotto.domain.numbergenerator;
import lombok.AllArgsConstructor;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;
import pl.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;
import pl.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import pl.lotto.domain.numberreceiver.NumberReceiverFacade;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
public class WinningNumbersGeneratorFacade {
    private RandomNumbersGenerable randomNumbersGenerator;
    private WinningNumbersValidator validator;
    private DrawDateFacade drawDateFacade;
    private WinningNumbersRepository winningNumbersRepository;
    private WinningNumberGeneratorFacadeConfigurationProperties properties;
    public WinningNumbersDto generateWinningNumbers() {
        LocalDateTime drawDate = drawDateFacade.getNextDrawDate();
        SixRandomNumbersDto sixRandomNumbersDto = randomNumbersGenerator.generateSixRandomNumbers(properties.minBound(), properties.maxBound(), properties.count());
        Set<Integer> winningNumbers = sixRandomNumbersDto.numbers();
        validator.validate(winningNumbers);
        WinningNumbers winningNumbersDocument = WinningNumbers.builder()
                .winningNumbers(winningNumbers)
                .drawDate(drawDate)
                .build();
        WinningNumbers savedWinningNumbers = winningNumbersRepository.save(winningNumbersDocument);
        return WinningNumbersDto.builder()
                .winningNumbers(savedWinningNumbers.winningNumbers())
                .drawDate(savedWinningNumbers.drawDate())
                .build();
    }

    public WinningNumbersDto retrieveWinningNumbersByDate(LocalDateTime date) {
        WinningNumbers winningNumbersByDate = winningNumbersRepository.findWinningNumbersByDrawDate(date)
                .orElseThrow(() -> new WinningNumbersNotFoundException("Not found"));
        return WinningNumbersDto.builder()
                .winningNumbers(winningNumbersByDate.winningNumbers())
                .drawDate(winningNumbersByDate.drawDate())
                .build();
    }

    public boolean areWinningNumbersGeneratedByDate() {
        LocalDateTime drawDate = drawDateFacade.getNextDrawDate();
        return winningNumbersRepository.existsByDrawDate(drawDate);
    }
}
