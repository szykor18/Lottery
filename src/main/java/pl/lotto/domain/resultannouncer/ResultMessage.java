package pl.lotto.domain.resultannouncer;

enum ResultMessage {
    WIN_MESSAGE("You won, congratulations!"),
    LOSE_MESSAGE("You lose, try again!"),
    WAIT_MESSAGE("Results are being calculated, please wait"),
    HASH_NOT_EXISTS_MESSAGE("Ticket does not exists"),
    ALREADY_CHECKED_MESSAGE("You have already checked your ticket, please come back later");
    final String info;
    ResultMessage(String info) {
        this.info = info;
    }
}
