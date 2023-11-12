package pl.lotto.domain.numbergenerator;
import pl.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;

import java.util.Set;

public class WinningNumbersGeneratorTestImpl implements RandomNumbersGenerable{
    private final Set<Integer> generatedNumbers;
    WinningNumbersGeneratorTestImpl(Set<Integer> generatedNumbers) {
        this.generatedNumbers = generatedNumbers;
    }
    WinningNumbersGeneratorTestImpl() {
        this.generatedNumbers = Set.of(1, 2, 3, 4, 5, 6);
    }
    @Override
    public SixRandomNumbersDto generateSixRandomNumbers() {
        return SixRandomNumbersDto.builder()
                .numbers(generatedNumbers)
                .build();
    }
}
