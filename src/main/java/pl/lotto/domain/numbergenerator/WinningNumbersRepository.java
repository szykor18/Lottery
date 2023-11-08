package pl.lotto.domain.numbergenerator;

import java.time.LocalDateTime;
import java.util.Optional;


public interface WinningNumbersRepository {
    WinningNumbers save(WinningNumbers winningNumbers);
    Optional<WinningNumbers> findWinningNumbersByDate(LocalDateTime drawDate);

    boolean existsByDate(LocalDateTime drawDate);
}
