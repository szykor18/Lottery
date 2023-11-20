package pl.lotto.domain.resultchecker;

class PlayerNotFoundByHashException extends RuntimeException{
    PlayerNotFoundByHashException(String hash) {
        super(String.format("Not found for id: %s", hash));
    }
}
