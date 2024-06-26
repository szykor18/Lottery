package pl.lotto.domain.numberreceiver;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.lotto.persistence.model.Ticket;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {

    List<Ticket> findAllByDrawDate(LocalDateTime date);
}
