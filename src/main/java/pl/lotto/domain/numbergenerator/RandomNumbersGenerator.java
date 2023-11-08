package pl.lotto.domain.numbergenerator;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class RandomNumbersGenerator implements RandomNumbersGenerable{
    private final int maximalRangeOfNumber = 99;
    private final int minimalRangeOfNumber = 1;
    private final int random_number_bound = (maximalRangeOfNumber - minimalRangeOfNumber) + minimalRangeOfNumber;
    @Override
    public Set<Integer> generateSixRandomNumbers() {
        Set<Integer> winningNumbers = new HashSet<>();
        while (sizeOfWinningNumbersIsLowerThanSix(winningNumbers)) {
            winningNumbers.add(getRandomNumber());
        }
        return winningNumbers;
    }
    private boolean sizeOfWinningNumbersIsLowerThanSix(Set<Integer> winningNumbers) {
        return winningNumbers.size() < 6;
    }
    private int getRandomNumber() {
        Random random = new SecureRandom();
        return random.nextInt(random_number_bound) + 1;
    }
}
