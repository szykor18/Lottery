package pl.lotto.domain.resultchecker;

public class TicketFoundButDrawNotHappenedYet extends RuntimeException{

    public TicketFoundButDrawNotHappenedYet(String message) {
        super(message);
    }
}
