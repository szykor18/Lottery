package pl.lotto.domain.numbergenerator;

import java.util.Set;

class WinningNumbersValidator {
    private final int maximalRangeOfNumber = 99;
    private final int minimalRangeOfNumber = 1;

    Set<Integer> validate(Set<Integer> winningNumbers) {
        if (outOfRange(winningNumbers)) {
            throw new IllegalStateException("Number out of range!");
        }
        return winningNumbers;
    }

    private boolean outOfRange(Set<Integer> winningNumbers) {
        return winningNumbers.stream()
                .anyMatch(number -> number > maximalRangeOfNumber || number < minimalRangeOfNumber);
    }
}
