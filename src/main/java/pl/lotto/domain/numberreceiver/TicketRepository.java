package pl.lotto.domain.numberreceiver;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket, String> {

    List<Ticket> findAllTicketsByDrawDate(LocalDateTime date);
}
