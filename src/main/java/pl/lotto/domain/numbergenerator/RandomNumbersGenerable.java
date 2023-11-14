package pl.lotto.domain.numbergenerator;
import pl.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;

public interface RandomNumbersGenerable {
    SixRandomNumbersDto generateSixRandomNumbers(int minBound, int maxBound, int count);
}
