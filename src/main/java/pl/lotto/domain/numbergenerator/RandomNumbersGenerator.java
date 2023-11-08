package pl.lotto.domain.numbergenerator;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class RandomNumbersGenerator implements RandomNumbersGenerable{
    private final int VALID_SIZE_OF_WINNING_NUMBERS = 6;
    private final int MAXIMAL_RANGE_OF_NUMBER = 99;
    private final int MINIMAL_RANGE_OF_NUMBER = 1;
    private final int RANDOM_NUMBER_BOUND = (MAXIMAL_RANGE_OF_NUMBER - MINIMAL_RANGE_OF_NUMBER) + MINIMAL_RANGE_OF_NUMBER;
    @Override
    public Set<Integer> generateSixRandomNumbers() {
        Set<Integer> winningNumbers = new HashSet<>();
        while (sizeOfWinningNumbersIsLowerThanSix(winningNumbers)) {
            winningNumbers.add(getRandomNumber());
        }
        return winningNumbers;
    }
    private boolean sizeOfWinningNumbersIsLowerThanSix(Set<Integer> winningNumbers) {
        return winningNumbers.size() < VALID_SIZE_OF_WINNING_NUMBERS;
    }
    private int getRandomNumber() {
        Random random = new SecureRandom();
        return random.nextInt(RANDOM_NUMBER_BOUND) + 1;
    }
}
