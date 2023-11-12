package pl.lotto.domain.numbergenerator;
import pl.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class SecureRandomNumbersGenerator implements RandomNumbersGenerable{
    private final int VALID_SIZE_OF_WINNING_NUMBERS = 6;
    private final int MAXIMAL_RANGE_OF_NUMBER = 99;
    private final int MINIMAL_RANGE_OF_NUMBER = 1;
    private final int RANDOM_NUMBER_BOUND = (MAXIMAL_RANGE_OF_NUMBER - MINIMAL_RANGE_OF_NUMBER) + MINIMAL_RANGE_OF_NUMBER;
    @Override
    public SixRandomNumbersDto generateSixRandomNumbers() {
        Set<Integer> winningNumbers = new HashSet<>();
        while (sizeOfWinningNumbersIsLowerThanSix(winningNumbers)) {
            winningNumbers.add(getRandomNumber());
        }
        return SixRandomNumbersDto.builder()
                .numbers(winningNumbers)
                .build();
    }
    private boolean sizeOfWinningNumbersIsLowerThanSix(Set<Integer> winningNumbers) {
        return winningNumbers.size() < VALID_SIZE_OF_WINNING_NUMBERS;
    }
    private int getRandomNumber() {
        Random random = new SecureRandom();
        return random.nextInt(RANDOM_NUMBER_BOUND) + 1;
    }
}
