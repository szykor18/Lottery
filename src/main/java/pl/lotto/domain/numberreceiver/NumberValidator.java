package pl.lotto.domain.numberreceiver;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class NumberValidator {

    private static final int MINIMAL_NUMBER_FROM_USER = 1;
    private static final int MAXIMAL_NUMBER_FROM_USER = 99;
    private static final int VALID_AMOUNT_OF_NUMBERS_FROM_USER = 6;
    List<ValidationResult> errors;

    List<ValidationResult> validate(Set<Integer> numbersFromUser) {
        errors = new LinkedList<>();
        if (!areAllNumbersInRange(numbersFromUser)) {
            errors.add(ValidationResult.NOT_IN_RANGE);
        }
        if (!isNumbersSizeEqualSix(numbersFromUser)) {
            errors.add(ValidationResult.NOT_SIX_NUMBERS_GIVEN);
        }
        return errors;
    }
    String createResultMessage() {
        return this.errors.stream()
                .map(validationResult -> validationResult.info)
                .collect(Collectors.joining(","));
    }

    private boolean areAllNumbersInRange(Set<Integer> numbersFromUser) {
        return numbersFromUser.stream()
                .allMatch(number -> number >= MINIMAL_NUMBER_FROM_USER
                        && number <= MAXIMAL_NUMBER_FROM_USER);
    }
    private boolean isNumbersSizeEqualSix(Set<Integer> numbersFromUser) {
        return numbersFromUser.size() == VALID_AMOUNT_OF_NUMBERS_FROM_USER;
    }
}
