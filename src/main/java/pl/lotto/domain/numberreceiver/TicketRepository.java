package pl.lotto.domain.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository {
    Ticket save(Ticket ticket);

    List<Ticket> findAllTicketsByDrawDate(LocalDateTime date);
}
