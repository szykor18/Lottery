package pl.lotto.domain.numbergenerator;

class WinningNumbersNotFoundException extends RuntimeException{
    WinningNumbersNotFoundException(String message) {
        super(message);
    }
}
