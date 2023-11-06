package pl.lotto.domain.numbergenerator;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class WinningNumbersRepositoryTestImpl implements WinningNumbersRepository{
    private Map<LocalDateTime, WinningNumbers> winningNumbersDatabase = new ConcurrentHashMap<>();
    @Override
    public WinningNumbers save(WinningNumbers winningNumbers) {
        return winningNumbersDatabase.put(winningNumbers.drawDate(), winningNumbers);
    }

    @Override
    public Optional<WinningNumbers> findWinningNumbersByDate(LocalDateTime drawDate) {
        return Optional.ofNullable(winningNumbersDatabase.get(drawDate));
    }

    @Override
    public boolean existsByDate(LocalDateTime drawDate) {
        return winningNumbersDatabase.containsKey(drawDate);
    }
}
