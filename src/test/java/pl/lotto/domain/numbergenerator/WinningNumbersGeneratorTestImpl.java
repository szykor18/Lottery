package pl.lotto.domain.numbergenerator;
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
    public Set<Integer> generateSixRandomNumbers() {
        return generatedNumbers;
    }
}
