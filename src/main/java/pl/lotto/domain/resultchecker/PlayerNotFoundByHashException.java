package pl.lotto.domain.resultchecker;

class PlayerNotFoundByHashException extends RuntimeException{
    PlayerNotFoundByHashException(String message) {
        super(message);
    }
}
