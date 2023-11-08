package pl.lotto.domain.numbergenerator;

import java.util.Set;

class WinningNumbersValidator {
    private final int MAXIMAL_RANGE_OF_NUMBER = 99;
    private final int MINIMAL_RANGE_OF_NUMBER = 1;

    Set<Integer> validate(Set<Integer> winningNumbers) {
        if (outOfRange(winningNumbers)) {
            throw new IllegalStateException("Number out of range!");
        }
        return winningNumbers;
    }

    private boolean outOfRange(Set<Integer> winningNumbers) {
        return winningNumbers.stream()
                .anyMatch(number -> number > MAXIMAL_RANGE_OF_NUMBER || number < MINIMAL_RANGE_OF_NUMBER);
    }
}
