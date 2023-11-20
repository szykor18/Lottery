package pl.lotto.domain.resultchecker;

public class PlayerNotFoundByHashException extends RuntimeException{
    PlayerNotFoundByHashException(String hash) {
        super(String.format("Not found for id: %s", hash));
    }
}
