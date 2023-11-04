package pl.lotto.domain.numberreveiver;

import java.util.Set;

class NumberValidator {

    private static final int MINIMAL_NUMBER_FROM_USER = 1;
    private static final int MAXIMAL_NUMBER_FROM_USER = 99;
    private static final int VALID_AMOUNT_OF_NUMBERS_FROM_USER = 6;

    boolean areAllNumbersInRange(Set<Integer> numbersFromUser) {
        return numbersFromUser.stream()
                .filter(number -> number >= MINIMAL_NUMBER_FROM_USER)
                .filter(number -> number <= MAXIMAL_NUMBER_FROM_USER)
                .count() == VALID_AMOUNT_OF_NUMBERS_FROM_USER;
    }
}
